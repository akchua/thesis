package com.thesis.tremor.rest.handler;

import java.util.List;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.SessionFormBean;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.entity.SessionComment;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface SessionHandler {

	Session getSession(Long sessionId);
	
	SessionComment getSessionComment(Long sessionCommentId);
	
	ObjectList<Session> getSessionObjectList(Integer pageNumber, DateDuration dateDuration, Long patientId);

	ObjectList<Session> getRecentSessionObjectList(Integer pageNumber, Integer daysAgo, Long doctorId);
	
	List<SessionComment> getSessionCommentList(Long sessionId);
	
	ResultBean saveSession(SessionFormBean sessionForm, String username, String password);

	ResultBean addComment(Long sessionId, String comment);
	
	ResultBean editComment(Long sessionCommentId, String comment);
	
	ResultBean removeComment(Long sessionCommentId);
}
