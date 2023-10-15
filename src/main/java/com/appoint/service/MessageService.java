package com.appoint.service;

import java.util.List;

import com.appoint.entity.Doctor;
import com.appoint.entity.Message;
import com.appoint.entity.Patient;
import com.appoint.exception.DoctorException;
import com.appoint.exception.PatientException;

public interface MessageService {
	
	Message sendMessageFromPatientToDoctor(String key, Message message) throws PatientException, DoctorException;

	Message sendMessageFromDoctorToPatient(String key, Message message) throws PatientException, DoctorException;

	List<Message> getMessageOfPatientParticularDoctor(String key, Doctor doctor) throws DoctorException, PatientException;

	List<Message> getMessageOfDoctorParticularPatient(String key, Patient patient) throws DoctorException, PatientException;

}
