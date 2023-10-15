package com.appoint.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import com.appoint.service.DoctorLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appoint.entity.CurrentSession;
import com.appoint.entity.Doctor;
import com.appoint.dto.LoginDTO;
import com.appoint.entity.LoginUUIDKey;
import com.appoint.exception.LoginException;
import com.appoint.repository.DoctorRepository;
import com.appoint.repository.SessionRepository;

@Service
public class DoctorLoginServiceImpl implements DoctorLoginService {
	
	@Autowired
    DoctorRepository doctorRepository;
	@Autowired
	SessionRepository sessionRepository;

	@Override
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException {
		LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
		Doctor existingDoctor = doctorRepository.findByMobileNo(loginDTO.getMobileNo());
		if(existingDoctor == null) {
			throw new LoginException("Please enter valid mobile number " + loginDTO.getMobileNo());
		}
		// please do uncomment this code while using this application in postman
//		Optional<CurrentSession> validCustomerSessionOpt = sessionDao.findById(existingDoctor.getDoctorId());
//		if(validCustomerSessionOpt.isPresent()) {
// 			throw new LoginException("Doctor already login");
//		}
		if(PatientServiceImpl.bCryptPasswordEncoder.matches(loginDTO.getPassword(), existingDoctor.getPassword())) {
			// check doctor have permission or not
			if(existingDoctor.getValidDoctor() == false) {
				throw new LoginException("You don't have permission to login. Please contact Admin for permission.");
			}
//		if(existingDoctor.getPassword().equals(loginDTO.getPassword())) {
			String key = generateRandomString();
			CurrentSession currentPatientSession = new CurrentSession(existingDoctor.getDoctorId(), key, LocalDateTime.now());
			existingDoctor.setType("doctor");
			currentPatientSession.setUserId(existingDoctor.getDoctorId());
			currentPatientSession.setUserType("doctor");
			doctorRepository.save(existingDoctor);
			sessionRepository.save(currentPatientSession);
			loginUUIDKey.setMsg("Login Successful as doctor with this key");
			loginUUIDKey.setUuid(key);
			return loginUUIDKey;
		}else {
			throw new LoginException("Please enter valid password");
		}
	}
	
	

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		CurrentSession currentDoctorOptional = sessionRepository.findByUuid(key);
		if(currentDoctorOptional != null) {
			sessionRepository.delete(currentDoctorOptional);
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
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}


}
