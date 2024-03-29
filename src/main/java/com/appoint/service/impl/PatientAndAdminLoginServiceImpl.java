package com.appoint.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import com.appoint.service.PatientAndAdminLoginService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appoint.entity.CurrentSession;
import com.appoint.dto.LoginDTO;
import com.appoint.entity.LoginUUIDKey;
import com.appoint.entity.Patient;
import com.appoint.exception.LoginException;
import com.appoint.repository.SessionRepository;


import com.appoint.repository.PatientRepository;

@Service
public class PatientAndAdminLoginServiceImpl implements PatientAndAdminLoginService {
	
	@Autowired
    PatientRepository patientRepository;
	
	@Autowired
	SessionRepository sessionRepository;

	@Override
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException {
		LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
		Patient existingPatient = patientRepository.findByMobileNo(loginDTO.getMobileNo());
		if(existingPatient == null) {
			throw new LoginException("Please enter valid mobile number " + loginDTO.getMobileNo());
		}
		Optional<CurrentSession> validCustomerSessionOpt = sessionRepository.findById(existingPatient.getPatientId());
		////////////////////////////////
		// this code is for only frontend application
		if(validCustomerSessionOpt.isPresent()) {
			if(PatientServiceImpl.bCryptPasswordEncoder.matches(loginDTO.getPassword(), existingPatient.getPassword())){
				loginUUIDKey.setUuid(validCustomerSessionOpt.get().getUuid());
				loginUUIDKey.setMsg("Login Successful");
				return loginUUIDKey;
			}
			throw new LoginException("Please enter valid details");
		}
		/////////////////////////////////////////////
		// please do uncomment this code while using this application in postman
//		if(validCustomerSessionOpt.isPresent()) {
//			
//			throw new LoginException("User already login");
//			
//		}
		if(PatientServiceImpl.bCryptPasswordEncoder.matches(loginDTO.getPassword(), existingPatient.getPassword())) {
			String key = generateRandomString();
			CurrentSession currentPatientSession = new CurrentSession(existingPatient.getPatientId(), key, LocalDateTime.now());
			if(PatientServiceImpl.bCryptPasswordEncoder.matches("admin", existingPatient.getPassword()) && existingPatient.getMobileNo().equals("1734467273")) {
				existingPatient.setType("admin");
				currentPatientSession.setUserType("admin");
				currentPatientSession.setUserId(existingPatient.getPatientId());
				sessionRepository.save(currentPatientSession);
				patientRepository.save(existingPatient);
				loginUUIDKey.setMsg("Login Successful as admin with key");
				loginUUIDKey.setUuid(key);
				return loginUUIDKey;
			}else {
				existingPatient.setType("patient");
				currentPatientSession.setUserId(existingPatient.getPatientId());
				currentPatientSession.setUserType("patient");
			}
			patientRepository.save(existingPatient);
			sessionRepository.save(currentPatientSession);
			loginUUIDKey.setMsg("Login Successful as patient with this key");
			loginUUIDKey.setUuid(key);
			return loginUUIDKey;
		}else {
			throw new LoginException("Please enter valid password");
		}
	}

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		CurrentSession currentPatientOptional = sessionRepository.findByUuid(key);
		if(currentPatientOptional != null) {
			sessionRepository.delete(currentPatientOptional);
			return "Logout successful";
		}else {
			throw new LoginException("Please enter valid key");
		}
	}
	
	@Override
	public Boolean checkUserLoginOrNot(String key) throws LoginException {
		CurrentSession currentPatientSession = sessionRepository.findByUuid(key);
		if(currentPatientSession != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public static String generateRandomString() {
		String keyvalu = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * keyvalu.length());
            salt.append(keyvalu.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}

}
