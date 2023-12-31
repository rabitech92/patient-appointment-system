package com.appoint.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.appoint.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appoint.entity.CurrentSession;
import com.appoint.entity.Doctor;
import com.appoint.entity.Message;
import com.appoint.entity.Patient;
import com.appoint.exception.DoctorException;
import com.appoint.exception.PatientException;
import com.appoint.repository.DoctorRepository;
import com.appoint.repository.MessageRepository;
import com.appoint.repository.PatientRepository;
import com.appoint.repository.SessionRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	MessageRepository messageRepository;
	@Override
	public Message sendMessageFromPatientToDoctor(String key, Message message) throws PatientException, DoctorException {
		CurrentSession currentPatientSession = sessionRepository.findByUuid(key);
		Optional<Patient> registerPatient = patientRepository.findById(currentPatientSession.getUserId());
		// check doctor is present in database or not
		Optional<Doctor> registerDoctor = doctorRepository.findById(message.getDoctor().getDoctorId());
		if(registerDoctor.isEmpty()) {
			throw new DoctorException("Doctor not found in database");
		}
		if(registerPatient.isPresent()) {
			// set patient and doctor in message
			message.setPatient(registerPatient.get());
			message.setDoctor(registerDoctor.get());
			// set sender and receiver id in message entity
			message.setSender(registerPatient.get().getPatientId());
			message.setReceiver(message.getDoctor().getDoctorId());
			
			
			
			// set time and date
			message.setMessageTimeAndDate(LocalDateTime.now());
//			// save message and return
			Message registerMessage = messageRepository.save(message);
			// set message in patinent and doctor
			registerDoctor.get().getListOfMessage().add(registerMessage);
			doctorRepository.save(registerDoctor.get());
			registerPatient.get().getListOfMessage().add(registerMessage);
			patientRepository.save(registerPatient.get());
			return registerMessage;
		}else {
			throw new PatientException("Please enter valid patient details");
		}
		
	}

	@Override
	public Message sendMessageFromDoctorToPatient(String key, Message message) throws PatientException, DoctorException {
		CurrentSession currentDoctor = sessionRepository.findByUuid(key);
		Optional<Doctor> registerDoctor = doctorRepository.findById(currentDoctor.getUserId());
		Optional<Patient> registerPatient = patientRepository.findById(message.getPatient().getPatientId());
		if(registerPatient.isEmpty()) {
			throw new DoctorException("Patient not found in database");
		}
		if(registerDoctor.isPresent()) {
			// set patient and doctor in message
			message.setPatient(registerPatient.get());
			message.setDoctor(registerDoctor.get());
			// set sender and receiver id in message entity
			message.setSender(message.getDoctor().getDoctorId());
			message.setReceiver(registerPatient.get().getPatientId());
			// set time and date
			message.setMessageTimeAndDate(LocalDateTime.now());
//			// save message and return
			Message registerMessage = messageRepository.save(message);
			// set message in patinent and doctor
			registerDoctor.get().getListOfMessage().add(registerMessage);
			doctorRepository.save(registerDoctor.get());
			registerPatient.get().getListOfMessage().add(registerMessage);
			patientRepository.save(registerPatient.get());
			return registerMessage;
		}else {
			throw new PatientException("Please enter valid doctor details");
		}
	}

	@Override
	public List<Message> getMessageOfPatientParticularDoctor(String key, Doctor doctor) throws DoctorException, PatientException {
		CurrentSession currentPatientSession = sessionRepository.findByUuid(key);
		Optional<Patient> registerPatient = patientRepository.findById(currentPatientSession.getUserId());
		// check doctor is present in database or not
		Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorId());
		if(registerPatient.isPresent()) {
			if(registerDoctor.isPresent()) {
				List<Message> listOfAllMessage = registerPatient.get().getListOfMessage();
				List<Message> getParticularDoctorMessage = listOfAllMessage.stream().filter(eachMessage ->{
					int result = eachMessage.getDoctor().getDoctorId().compareTo(doctor.getDoctorId());
					if(result == 0) {
						return true;
					}else {
						return false;
					}
				} ).collect(Collectors.toList());
				return getParticularDoctorMessage;
			}else {
				throw new DoctorException("Doctor not found");
			}
		}else {
			throw new PatientException("Patient not found");
		}
	}

	@Override
	public List<Message> getMessageOfDoctorParticularPatient(String key, Patient patient) throws DoctorException, PatientException {
		CurrentSession currentDoctor = sessionRepository.findByUuid(key);
		Optional<Doctor> registerDoctor = doctorRepository.findById(currentDoctor.getUserId());
		Optional<Patient> registerPatient = patientRepository.findById(patient.getPatientId());
		if(registerDoctor.isPresent()) {
			if(registerPatient.isPresent()) {
				List<Message> listOfAllMessage = registerDoctor.get().getListOfMessage();
				List<Message> getParticularDoctorMessage = listOfAllMessage.stream().filter(eachMessage ->{
					int result = eachMessage.getPatient().getPatientId().compareTo(patient.getPatientId());
					if(result == 0) {
						return true;
					}else {
						return false;
					}
				} ).collect(Collectors.toList());
				return getParticularDoctorMessage;
			}else {
				throw new DoctorException("Patient not found");
			}
		}else {
			throw new PatientException("Doctor not found");
		}
	}

}










