package com.thesis.tremor.database.service;

import java.util.List;

import com.thesis.tremor.database.entity.DoctorPatient;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.prototype.DoctorPatientPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   18 Aug 2017
 */
public interface DoctorPatientService extends Service<DoctorPatient, Long>, DoctorPatientPrototype {

	ObjectList<User> findAllPatientsByDoctorWithPagingOrderByName(int pageNumber, int resultsPerPage, String searchKey, Long doctorId);
	
	List<User> findAllPatientByDoctor(Long doctorId);

	List<User> findAllDoctorByPatient(Long patientId);
}
