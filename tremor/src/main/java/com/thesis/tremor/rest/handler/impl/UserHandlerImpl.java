package com.thesis.tremor.rest.handler.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.beans.PasswordFormBean;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.UserFormBean;
import com.thesis.tremor.constants.MailConstants;
import com.thesis.tremor.database.entity.DoctorPatient;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.DoctorPatientService;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.enums.Color;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.UserHandler;
import com.thesis.tremor.utility.EmailUtil;
import com.thesis.tremor.utility.EncryptionUtil;
import com.thesis.tremor.utility.Html;
import com.thesis.tremor.utility.StringGenerator;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   25 Jul 2017
 */
@Transactional
@Component
public class UserHandlerImpl implements UserHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorPatientService doctorPatientService;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public User getUser(Long userId) {
		return userService.find(userId);
	}
	
	@Override
	public ObjectList<User> getUserObjectList(Integer pageNumber, String searchKey) {
		return userService.findAllWithPagingOrderByName(pageNumber, UserContextHolder.getItemsPerPage(), searchKey);
	}
	
	@Override
	public ObjectList<User> getPatientObjectListByDoctor(Integer pageNumber, String searchKey, Long doctorId) {
		return doctorPatientService.findAllPatientsByDoctorWithPagingOrderByName(pageNumber, UserContextHolder.getItemsPerPage(), searchKey, doctorId);
	}
	
	@Override
	public ResultBean createUser(UserFormBean userForm) {
		final ResultBean result;
		final ResultBean validateForm = validateUserForm(userForm);
		
		if(validateForm.getSuccess()) {
			final ResultBean validatePassword = validatePassword(userForm);
			if(validatePassword.getSuccess()) {
				if(userService.isExistByUsername(userForm.getUsername().trim())) {
					result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Username already exists.") + "  Please choose another username."));
				} else {
					final User user = new User();
					
					user.setPassword(EncryptionUtil.getMd5(userForm.getPassword()));
					setUser(user, userForm);
					
					result = new ResultBean();
					result.setSuccess(userService.insert(user) != null);
					if(result.getSuccess()) {
						result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " created account of " + Html.text(Color.BLUE, user.getFormattedName()) + ". Thank you."));
					} else {
						result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
					}
				}
			} else {
				result = validatePassword;
			}
		} else {
			result = validateForm;
		}
		
		return result;
	}
	
	@Override
	public ResultBean addPatient(String username, String password) {
		final ResultBean result;
		final User doctor = UserContextHolder.getUser().getUserEntity();
		
		if(doctor != null && doctor.getUserType().equals(UserType.DOCTOR)) {
			final User patient = userService.findByUsernameAndPassword(username, EncryptionUtil.getMd5(password));
			
			if(patient != null && patient.getUserType().equals(UserType.PATIENT)) {
				final DoctorPatient doctorPatient = new DoctorPatient();
				
				doctorPatient.setDoctor(doctor);
				doctorPatient.setPatient(patient);
				
				result = new ResultBean();
				result.setSuccess(doctorPatientService.insert(doctorPatient) != null);
				if(result.getSuccess()) {
					result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " added " + Html.text(Color.BLUE, patient.getFormattedName()) + " as patient of " + Html.text(Color.BLUE, doctor.getFormattedName()) + "."));
				} else {
					result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
				}
			} else {
				result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load patient. Please re-enter correct credentials."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load doctor. Please re-log your account."));
		}
		
		return result;
	}
	
 	@Override
	public ResultBean updateUser(UserFormBean userForm) {
		final ResultBean result;
		final User user = userService.find(userForm.getId());
		
		if(user != null) {
			final ResultBean validateForm = validateUserForm(userForm);
			
			if(validateForm.getSuccess()) {
				final User uzer = userService.findByUsername(userForm.getUsername());
				if(uzer != null && user.getId() != uzer.getId()) {
					result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Username already exists.") + " Please choose another username."));
				} else {
					setUser(user, userForm);
					
					result = new ResultBean();
					result.setSuccess(userService.update(user));
					if(result.getSuccess()) {
						if(UserContextHolder.getUser().getId().equals(user.getId())) {
							UserContextHolder.refreshUser(user);
						}
						result.setMessage(Html.line("Profile has been " + Html.text(Color.GREEN, "updated") + "."));
					} else {
						result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
					}
				}
			} else {
				result = validateForm;
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load user. Please re-log your account."));
		}
		
		return result;
	}

	@Override
	public ResultBean removeUser(Long userId) {
		final ResultBean result;
		
		if(userId != UserContextHolder.getUser().getId()) {
			final User user = userService.find(userId);
			if(user != null) {
				if(user.getUserType().equals(UserType.ADMINISTRATOR)) {
					result = new ResultBean(Boolean.FALSE, Html.line("You are " + Html.text(Color.RED, "NOT ALLOWED") + " to delete an admin account.")
														+ Html.line("Please contact your database administrator for assistance."));
				} else {
					result = new ResultBean();
					
					result.setSuccess(userService.delete(user));
					if(result.getSuccess()) {
						result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " removed the account of Mr./Ms. " + Html.text(Color.BLUE, user.getFirstName() + " " + user.getLastName()) + "."));
					} else {
						result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
					}
				}
			} else {
				result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load user. Please refresh the page."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line("You " + Html.text(Color.RED, "CANNOT") + " delete your account."));
		}
		
		return result;
	}
	
	@Override
	public ResultBean removePatient(Long patientId) {
		final ResultBean result;
		final DoctorPatient doctorPatient = doctorPatientService.findByDoctorAndPatientId(UserContextHolder.getUser().getId(), patientId);
		
		if(doctorPatient != null && doctorPatient.getPatient().getUserType().equals(UserType.PATIENT)) {
			result = new ResultBean();
			
			result.setSuccess(doctorPatientService.delete(doctorPatient));
			if(result.getSuccess()) {
				result.setMessage(Html.line(Html.text(Color.GREEN, "Successfully") + " removed patient Mr./Ms. " + Html.text(Color.BLUE, doctorPatient.getPatient().getFirstName() + " " + doctorPatient.getPatient().getLastName()) + " from your list of patients."));
			} else {
				result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load patient. Please refresh the page."));
		}
		
		return result;
	}
	
	@Override
	public ResultBean resetPassword(Long userId) {
		final ResultBean result;
		final User user = userService.find(userId);
		
		if(user != null) {
			result = new ResultBean();
			String randomPassword = StringGenerator.nextString();
			
			user.setPassword(EncryptionUtil.getMd5(randomPassword));
			result.setSuccess(userService.update(user) &&
					emailUtil.send(user.getEmailAddress(),
					null,
					MailConstants.DEFAULT_EMAIL + ", " +  getEmailOfAllAdmin(),
					"Tremor Pro Reset Password",
					"Hi " + user.getFirstName() + " " + user.getLastName() + ", your Tremor Pro account password has just been reset."
						+ "\nYour new credentials are : "
						+ "\n\nUsername - " + user.getUsername()
						+ "\nPasswrod - " + randomPassword
						+ "\n\nPlease login at test.url.com and change your password as soon as possible.",
					null));
			
			if(result.getSuccess()) {
				result.setMessage(Html.line(Color.GREEN, "Password successfully reset.") 
						+ Html.line("New password sent to " + Html.text(Color.BLUE, user.getEmailAddress()) + "."));
			} else {
				result.setMessage(Html.line(Html.text(Color.RED, "Server Error.") + " Please try again later."));
			}
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load user. Please re-log your account."));
		}
		
		return result;
	}
	
	@Override
	public String getEmailOfAllAdmin() {
		String emailOfAllAdmin = "";
		final List<User> administrators = userService.findAllAdministrators();
		
		for(User admin : administrators) {
			emailOfAllAdmin += admin.getEmailAddress() + ", ";
		}
		
		return emailOfAllAdmin;
	}
	
	@Override
	public List<UserType> getUserTypeList() {
		return Stream.of(UserType.values())
				.collect(Collectors.toList());
	}
	
	private void setUser(User user, UserFormBean userForm) {
		user.setUsername(userForm.getUsername().trim());
		user.setFirstName(userForm.getFirstName().trim());
		user.setLastName(userForm.getLastName().trim());
		user.setEmailAddress(userForm.getEmailAddress().trim());
		user.setContactNumber(userForm.getContactNumber().trim());
		user.setUserType(userForm.getUserType() != null ? userForm.getUserType() : UserType.PATIENT);
		if(user.getItemsPerPage() == null) user.setItemsPerPage(10);
		
		if(user.getUserType().equals(UserType.DOCTOR)) {
			user.setHospital_name(userForm.getHospital_name() != null ? userForm.getHospital_name().trim() : null);
			user.setHospital_address(userForm.getHospital_address() != null ? userForm.getHospital_address().trim() : null);
		}
		
		if(user.getUserType().equals(UserType.PATIENT)) {
			user.setBirthdate(userForm.getBirthdate() != null ? userForm.getBirthdate().trim() : null);
			user.setSex(userForm.getSex()!= null ? userForm.getSex().trim() : null);
			user.setAddress(userForm.getAddress()!= null ? userForm.getAddress().trim() : null);
		}
	}
	
	private ResultBean validateUserForm(UserFormBean userForm) {
		final ResultBean result;
		
		if(userForm.getUsername() == null || userForm.getUsername().trim().length() < 3 ||
				userForm.getFirstName() == null || userForm.getFirstName().trim().length() < 3 ||
				userForm.getLastName() == null || userForm.getLastName().trim().length() < 3 ||
				userForm.getEmailAddress() == null || userForm.getEmailAddress().trim().length() < 3 ||
				userForm.getContactNumber() == null || userForm.getContactNumber().trim().length() < 3 ||
				(userForm.getUserType().equals(UserType.DOCTOR) && 
						(userForm.getHospital_name() == null || userForm.getHospital_name().trim().length() < 3 ||
						 userForm.getHospital_address() == null || userForm.getHospital_address().trim().length() < 3)) ||
				(userForm.getUserType().equals(UserType.PATIENT) &&
						(userForm.getBirthdate() == null || userForm.getBirthdate().trim().length() < 3 ||
						 userForm.getSex() == null || userForm.getSex().trim().length() < 3 ||
						 userForm.getAddress() == null || userForm.getAddress().trim().length() < 3))) {
			result = new ResultBean(Boolean.FALSE, Html.line("All fields are " + Html.text(Color.RED, "required") + " and must contain at least 3 characters."));
		} else if(!userForm.getUsername().trim().matches("^[A-Za-z_]\\w{2,31}$")) {
			result = new ResultBean(Boolean.FALSE, Html.line(Color.RED, "Invalid Username!")
												+ Html.line("Username must be at least 3 to 30 characters and cannot contain white spaces and/or special characters."));
		} else if(!emailUtil.validateEmail(userForm.getEmailAddress().trim())) {
			result = new ResultBean(Boolean.FALSE, Html.line(Color.RED, "Invalid Email Address!"));
		} else {
			result = new ResultBean(Boolean.TRUE, "");
		}
		
		return result;
	}
	
	private ResultBean validatePassword(UserFormBean userForm) {
		return validatePassword(new PasswordFormBean("", userForm.getPassword(), userForm.getConfirmPassword()));
	}
	
	private ResultBean validatePassword(PasswordFormBean passwordForm) {
		final ResultBean result;
		
		if(passwordForm.getPassword() == null || passwordForm.getPassword().length() < 3 ||
				passwordForm.getConfirmPassword() == null || passwordForm.getConfirmPassword().length() < 3) {
			result = new ResultBean(Boolean.FALSE, Html.line("All fields are " + Html.text(Color.RED, "required") + " and must contain at least 3 characters."));
		} else if(!passwordForm.getPassword().equals(passwordForm.getConfirmPassword())) {
			result = new ResultBean(Boolean.FALSE, Html.line(Color.RED, "Confirm password does not match."));
		} else if(!passwordForm.getPassword().matches("((?=.*\\d)(?=.*[a-zA-Z])\\S{5,21})")) {
			result = new ResultBean(Boolean.FALSE, Html.line(Color.RED, "Invalid Password!")
												+ Html.line("Password must be at least 6 to 20 characters and cannot contain white spaces and must be a combination of letters and digits."));
		} else {
			result = new ResultBean(Boolean.TRUE, "");
		}
		
		return result;
	}
}
