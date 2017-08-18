package com.thesis.tremor.database.service.impl;

import java.util.stream.Collectors;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.DoctorPatientDAO;
import com.thesis.tremor.database.entity.DoctorPatient;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.DoctorPatientService;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   18 Aug 2017
 */
@Service
public class DoctorPatientServiceImpl
		extends AbstractService<DoctorPatient, Long, DoctorPatientDAO>
		implements DoctorPatientService {

	@Autowired
	protected DoctorPatientServiceImpl(DoctorPatientDAO dao) {
		super(dao);
	}

	@Override
	public ObjectList<User> findAllPatientsByDoctorWithPagingOrderByName(int pageNumber, int resultsPerPage,
			String searchKey, Long doctorId) {
		final ObjectList<User> patients = new ObjectList<User>();
		final ObjectList<DoctorPatient> doctorPatients = dao.findAllByDoctorWithPagingAndOrder(pageNumber, resultsPerPage, searchKey, doctorId, new Order[] { Order.asc("patientz.lastName, patientz.firstName") });
		patients.setList(doctorPatients.getList().stream()
								.map(dp -> dp.getPatient())
								.collect(Collectors.toList()));
		patients.setTotal(doctorPatients.getTotal());
		return patients;
	}
}
