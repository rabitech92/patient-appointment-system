package com.appoint.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appoint.entity.CurrentSession;
import com.appoint.entity.Doctor;
import com.appoint.entity.Message;
import com.appoint.entity.Patient;
import com.appoint.exception.DoctorException;
import com.appoint.exception.LoginException;
import com.appoint.exception.PatientException;
import com.appoint.service.DoctorLoginService;
import com.appoint.service.DoctorService;
import com.appoint.service.MessageService;
import com.appoint.service.PatientAndAdminLoginService;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	PatientAndAdminLoginService loginService;
	@Autowired
	DoctorLoginService doctorLoginService;
	@Autowired
	DoctorService doctorService;
	@Autowired
	MessageService messageService;

	@PostMapping("/patientToDoctor")
	@CrossOrigin
	public ResponseEntity<Message> sendMessageFromPatientToDoctor(@RequestParam String key, @RequestBody Message message) throws LoginException, PatientException, DoctorException{
		if(loginService.checkUserLoginOrNot(key)) {
			Message sendMessage = messageService.sendMessageFromPatientToDoctor(key, message);
			return new ResponseEntity<Message>(sendMessage, HttpStatus.OK);
		}else {
			throw new LoginException("Invalid key or please login first");
		}
	}
	
	@PostMapping("/patientMessage")
	@CrossOrigin
	public ResponseEntity<List<Message>> getMessageOfPatientParticularDoctor(@RequestParam String key, @RequestBody Doctor doctor) throws LoginException, DoctorException, PatientException{
		if(loginService.checkUserLoginOrNot(key)) {
			List<Message> listOfMessage = messageService.getMessageOfPatientParticularDoctor(key, doctor);
			return new ResponseEntity<List<Message>>(listOfMessage, HttpStatus.OK);
		}else {
			throw new LoginException("Invalid key or please login first");
		}
	}
	
	@PostMapping("/doctorToPatient")
	@CrossOrigin
	public ResponseEntity<Message> sendMessageFromDoctorToPatient(@RequestParam String key, @RequestBody Message message) throws LoginException, PatientException, DoctorException{
		if(doctorLoginService.checkUserLoginOrNot(key)) {
			CurrentSession currentUserSession = doctorService.getCurrentUserByUuid(key);
			Doctor registerDoctor = doctorService.getDoctorByUuid(key);
			if(!currentUserSession.getUserType().equals("doctor")) {
				throw new LoginException("Please login as doctor");
			}
			if(registerDoctor != null) {
				Message sendMessage = messageService.sendMessageFromDoctorToPatient(key, message);
				return new ResponseEntity<Message>(sendMessage, HttpStatus.OK);
			}else {
				throw new DoctorException("Please enter valid doctor details");
			}
		}else {
			throw new LoginException("Please enter valid key");
		}
	}
	
	@PostMapping("/doctorMessage")
	@CrossOrigin
	public ResponseEntity<List<Message>> getMessageOfDoctorParticularPatient(@RequestParam String key, @RequestBody Patient patient) throws LoginException, DoctorException, PatientException{
		if(doctorLoginService.checkUserLoginOrNot(key)) {
			CurrentSession currentUserSession = doctorService.getCurrentUserByUuid(key);
			Doctor registerDoctor = doctorService.getDoctorByUuid(key);
			if(!currentUserSession.getUserType().equals("doctor")) {
				throw new LoginException("Please login as doctor");
			}
			if(registerDoctor != null) {
				List<Message> listOfMessage = messageService.getMessageOfDoctorParticularPatient(key, patient);
				return new ResponseEntity<List<Message>>(listOfMessage, HttpStatus.OK);
			}else {
				throw new DoctorException("Please enter valid doctor details");
			}
		}else {
			throw new LoginException("Please enter valid key");
			
		}
		
	}

}
