package com.thesis.tremor.rest.handler;

import java.util.List;

import com.thesis.tremor.beans.PasswordFormBean;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.UserFormBean;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   25 Jul 2017
 */
public interface UserHandler {
	
	User getUser(Long userId);
	
	ObjectList<User> getUserObjectList(Integer pageNumber, String searchKey);
	
	ObjectList<User> getPatientObjectListByDoctorId(Integer pageNumber, String searchKey, Long doctorId);
	
	ResultBean createUser(UserFormBean userForm);
	
	ResultBean addPatient(String username, String password);
	
	ResultBean updateUser(UserFormBean userForm);
	
	ResultBean removeUser(Long userId);
	
	ResultBean changePassword(PasswordFormBean passwordForm);
	
	ResultBean removePatient(Long patientId);
	
	ResultBean resetPassword(Long userId);
	
	String getEmailOfAllAdmin();

	List<UserType> getUserTypeList();
}
