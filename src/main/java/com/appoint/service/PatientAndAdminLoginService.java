package com.appoint.service;

import com.appoint.dto.LoginDTO;
import com.appoint.entity.LoginUUIDKey;
import com.appoint.exception.LoginException;

public interface PatientAndAdminLoginService {
	
	LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException;
	String logoutFromAccount(String key) throws LoginException;
	Boolean checkUserLoginOrNot(String key) throws LoginException;

}
