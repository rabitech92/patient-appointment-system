package com.appoint.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.appoint.entity.Appointment;
import com.appoint.entity.CurrentSession;
import com.appoint.entity.Doctor;
import com.appoint.entity.Patient;
import com.appoint.entity.Review;
import com.appoint.exception.AppointmentException;
import com.appoint.exception.DoctorException;
import com.appoint.exception.LoginException;
import com.appoint.exception.PatientException;
import com.appoint.exception.ReviewException;
import com.appoint.exception.TimeDateException;


public interface DoctorService {
	
	List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException;
	
	Doctor getDoctorByUuid(String uuid) throws PatientException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException;
	
	List<Appointment> getUpcommingDoctorAppointment(Doctor doctor) throws AppointmentException;
	
	List<Appointment> getPastDoctorAppointment(Doctor doctor) throws AppointmentException;
	
	List<Appointment> getAllAppointments(Doctor registerDoctor) throws DoctorException;
	
	Doctor getDoctorDetails(String key) throws PatientException;

	Review getReviewOfParticularAppointment(String key, Appointment appointment) throws PatientException, ReviewException;

	List<Patient> getListOfPatient();
	
}
