package com.appoint.service;

import java.io.IOException;
import java.util.List;
import com.appoint.entity.Appointment;
import com.appoint.entity.CurrentSession;
import com.appoint.entity.Doctor;
import com.appoint.entity.ForgetPassword;
import com.appoint.entity.Patient;
import com.appoint.entity.Review;
import com.appoint.exception.AppointmentException;
import com.appoint.exception.DoctorException;
import com.appoint.exception.LoginException;
import com.appoint.exception.PasswordException;
import com.appoint.exception.PatientException;
import com.appoint.exception.ReviewException;
import com.appoint.exception.TimeDateException;

import jakarta.mail.MessagingException;


public interface PatientService {
	
	Patient createPatient(Patient customer) throws PatientException; 
	
	Patient updatePatient(Patient patient, String key) throws PatientException;
	
	Patient getPatientByUuid(String uuid) throws PatientException;
	
	CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException, IOException, TimeDateException, MessagingException;
	
	List<Appointment> getAllAppointmenPatientWise(String key)throws AppointmentException, PatientException;
	
	Appointment updateAppointment(String key, Appointment newAppointment) throws AppointmentException, PatientException, DoctorException, IOException, TimeDateException;
	
	List<Doctor> getAllDoctors() throws DoctorException;//15.10.2023
	
	Appointment deleteAppointment(Appointment appointment) throws AppointmentException, DoctorException, Exception;

	Review makeReviewToDoctorAppointment(String key, Review review) throws AppointmentException, DoctorException, ReviewException;
	
	Review updateReview(String key, Review review) throws ReviewException;

	Float getDoctorRating(String key, Doctor doctor) throws DoctorException, ReviewException;

	Patient getPatientDetails(String key) throws PatientException;
	
	Review getReviewOfDoctorByPatient(String key,Review review) throws ReviewException, PatientException, DoctorException, AppointmentException;

	Patient forgetPassword(String key, ForgetPassword forgetPassword) throws PasswordException;

	Review deleteReview(String key, Review review) throws ReviewException; 
	
}
