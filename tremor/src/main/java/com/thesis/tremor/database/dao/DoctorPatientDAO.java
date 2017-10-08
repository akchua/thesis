package com.thesis.tremor.database.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.thesis.tremor.database.entity.DoctorPatient;
import com.thesis.tremor.database.prototype.DoctorPatientPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   18 Aug 2017
 */
public interface DoctorPatientDAO extends DAO<DoctorPatient, Long>, DoctorPatientPrototype {

	ObjectList<DoctorPatient> findAllByDoctorWithPaging(int pageNumber, int resultsPerPage, String searchKey, Long doctorId);
	
	ObjectList<DoctorPatient> findAllByDoctorWithPagingAndOrder(int pageNumber, int resultsPerPage, String searchKey, Long doctorId, Order[] orders);
	
	List<DoctorPatient> findAllByDoctor(Long doctorId);

	List<DoctorPatient> findAllByPatient(Long patientId);
}
