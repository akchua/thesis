package com.thesis.tremor.rest.handler.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.beans.FingerFormBean;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.SessionFormBean;
import com.thesis.tremor.beans.TestFormBean;
import com.thesis.tremor.constants.MailConstants;
import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.database.entity.Hand;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.entity.SessionComment;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.DoctorPatientService;
import com.thesis.tremor.database.service.FingerService;
import com.thesis.tremor.database.service.HandService;
import com.thesis.tremor.database.service.SessionCommentService;
import com.thesis.tremor.database.service.SessionService;
import com.thesis.tremor.database.service.TestService;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.enums.Color;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.SessionHandler;
import com.thesis.tremor.utility.EmailUtil;
import com.thesis.tremor.utility.EncryptionUtil;
import com.thesis.tremor.utility.Html;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Transactional
@Component
public class SessionHandlerImpl implements SessionHandler {

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private SessionCommentService sessionCommentService;
	
	@Autowired
	private HandService handService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FingerService fingerService;
	
	@Autowired
	private DoctorPatientService doctorPatientService;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Override
	public Session getSession(Long sessionId) {
		return sessionService.find(sessionId);
	}
	
	@Override
	public SessionComment getSessionComment(Long sessionCommentId) {
		return sessionCommentService.find(sessionCommentId);
	}

	@Override
	public ObjectList<Session> getSessionObjectList(Integer pageNumber, DateDuration dateDuration, Long patientId) {
		return sessionService.findAllWithPaging(pageNumber, UserContextHolder.getItemsPerPage(), dateDuration, patientId);
	}
	
	@Override
	public ObjectList<Session> getRecentSessionObjectList(Integer pageNumber, Integer daysAgo, Long doctorId) {
		final List<User> patients = doctorPatientService.findAllPatientByDoctor(doctorId);
		final Calendar from = Calendar.getInstance();
		from.add(Calendar.DAY_OF_YEAR, -daysAgo);
		
		return sessionService.findAllWithPagingByPatientIdOrderByCreatedOn(
							pageNumber, 
							UserContextHolder.getItemsPerPage(), 
							new DateDuration(from.getTime(), new Date()), 
							patients.stream()
								.map(User::getId)
								.collect(Collectors.toList()));
	}
	
	@Override
	public List<SessionComment> getSessionCommentList(Long sessionId) {
		return sessionCommentService.findAllBySession(sessionId);
	}

	@Override
	public ResultBean saveSession(SessionFormBean sessionForm, String username, String password) {
		final ResultBean result;
		final User patient = userService.findPatientByUsernameAndPassword(username, EncryptionUtil.getMd5(password));
		
		if(patient != null) {
			final ResultBean validateForm = validateSessionForm(sessionForm);
			
			if(validateForm.getSuccess()) {
				if(!sessionService.isExistsByPatientAndDateDone(patient.getId(), sessionForm.getDateDone())) {
					result = new ResultBean();
					final Session session = new Session();
					
					setSession(session, sessionForm, patient);
					result.setSuccess(sessionService.insert(session) != null);
					if(result.getSuccess()) {
						for(TestFormBean testForm: sessionForm.getTests()) {
							final Hand leftHand = new Hand();
							final Hand rightHand = new Hand();
							
							result.setSuccess(handService.insert(leftHand) != null &&
												handService.insert(rightHand) != null);
							if(result.getSuccess()) {
								for(FingerFormBean fingerForm: testForm.getLeftHand().getFingers()) {
									final Finger finger = new Finger();
									setFinger(finger, fingerForm, leftHand);
									fingerService.insert(finger);
								}
								
								for(FingerFormBean fingerForm: testForm.getRightHand().getFingers()) {
									final Finger finger = new Finger();
									setFinger(finger, fingerForm, rightHand);
									fingerService.insert(finger);
								}
								
								final Test test = new Test();
								setTest(test, session, leftHand, rightHand, testForm);
								
								result.setSuccess(testService.insert(test) != null);
								
								if(!result.getSuccess()) break;
							} else break;
						}
						
						if(result.getSuccess()) {
							final Map<String, Object> extras = new HashMap<String, Object>();
							extras.put("sessionId", session.getId());
							result.setExtras(extras);
							result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " saved session for " + Html.text(Color.BLUE, patient.getFormattedName()) + "."));
							
							final List<User> doctors = doctorPatientService.findAllDoctorByPatient(patient.getId());
							for(User doctor : doctors) {
								emailUtil.send(
										doctor.getEmailAddress(),
										null,
										MailConstants.DEFAULT_EMAIL,
										"New Session Upload",
										"Your patient " + patient.getFormattedName() + " has just uploaded a new session.",
										null);
							}
						} else {
							throw new WebApplicationException(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."), Response.Status.INTERNAL_SERVER_ERROR);
						}
					} else {
						throw new WebApplicationException(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."), Response.Status.INTERNAL_SERVER_ERROR);
					}
				} else {
					result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Session already exists.")));
				}
			} else {
				result = validateForm;
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load patient. Please re-enter correct credentials."));
		}
		
		return result;
	}
	
	@Override
	public ResultBean addComment(Long sessionId, String comment) {
		final ResultBean result;
		final Session session = sessionService.find(sessionId);
		final ResultBean validateComment = validateComment(comment);
		
		if(session != null) {
			if(validateComment.getSuccess()) {
				result = new ResultBean();
				final SessionComment sessionComment = new SessionComment();
				
				sessionComment.setSession(session);
				sessionComment.setCommenter(UserContextHolder.getUser().getUserEntity());
				setSessionComment(sessionComment, comment);
				
				result.setSuccess(sessionCommentService.insert(sessionComment) != null);
				if(result.getSuccess()) {
					result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " added comment to session with ID " + Html.text(Color.BLUE, session.getId() + "") + "."));
				} else {
					result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
				}
			} else {
				result = validateComment;
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load session. Please reload the page"));
		}
		
		return result;
	}

	@Override
	public ResultBean editComment(Long sessionCommentId, String comment) {
		final ResultBean result;
		final SessionComment sessionComment = sessionCommentService.find(sessionCommentId);
		
		if(sessionComment != null) {
			result = new ResultBean();
			
			setSessionComment(sessionComment, comment);
			
			result.setSuccess(sessionCommentService.update(sessionComment));
			if(result.getSuccess()) {
				result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " edited comment of session with ID " + Html.text(Color.BLUE, sessionComment.getSession().getId() + "") + "."));
			} else {
				result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load session comment. Please reload the page"));
		}
		
		return result;
	}

	@Override
	public ResultBean removeComment(Long sessionCommentId) {
		final ResultBean result;
		final SessionComment sessionComment = sessionCommentService.find(sessionCommentId);
		
		if(sessionComment != null) {
			result = new ResultBean();
			
			result.setSuccess(sessionCommentService.delete(sessionCommentId));
			if(result.getSuccess()) {
				result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " removed comment from session with ID " + Html.text(Color.BLUE, sessionComment.getSession().getId() + "") + "."));
			} else {
				result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load session comment. Please reload the page"));
		}
		
		return result;
	}
	
	private void setSession(Session session, SessionFormBean sessionForm, User patient) {
		session.setDateDone(sessionForm.getDateDone());
		session.setPatient(patient);
	}
	
	private void setSessionComment(SessionComment sessionComment, String comment) {
		sessionComment.setComment(comment.trim());
	}
	
	private void setFinger(Finger finger, FingerFormBean fingerForm, Hand hand) {
		finger.setHand(hand);
		finger.setAverageAmplitude(fingerForm.getAverageAmplitude());
		finger.setAverageFrequency(fingerForm.getAverageFrequency());
		finger.setFingerPoints(
					fingerForm.getFingerPoints().stream()
						.map(fp -> String.valueOf(fp.getX()) + "," + String.valueOf(fp.getY()))
						.collect(Collectors.joining(";"))
				);
		finger.setFingerType(fingerForm.getFingerType());
	}
	
	private void setTest(Test test, Session session, Hand leftHand, Hand rightHand, TestFormBean testForm) {
		test.setSession(session);
		test.setLeftHand(leftHand);
		test.setRightHand(rightHand);
		
		test.setTestType(testForm.getTestType());
		test.setName(testForm.getName());
		test.setDuration(testForm.getDuration());
	}
	
	private ResultBean validateSessionForm(SessionFormBean sessionFormBean) {
		final ResultBean result;
		
		result = new ResultBean(Boolean.TRUE, "No validations applied yet");
		
		return result;
	}
	
	private ResultBean validateComment(String comment) {
		final ResultBean result;
		
		if(!comment.trim().isEmpty()) {
			result = new ResultBean(Boolean.TRUE, "");
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Empty comment.")));
		}
		
		return result;
	}
}
