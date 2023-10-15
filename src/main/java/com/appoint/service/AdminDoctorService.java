package com.appoint.service;


import java.util.List;

import com.appoint.entity.Doctor;
import com.appoint.exception.DoctorException;

public interface AdminDoctorService {
	
	
	Doctor registerDoctor(Doctor doctor) throws DoctorException;

	Doctor revokePermissionOfDoctor(Doctor doctor) throws DoctorException; 
	
	Doctor grantPermissionOfDoctor(Doctor doctor) throws DoctorException;
 
	List<Doctor> getAllValidInValidDoctors(String key) throws DoctorException;

}
