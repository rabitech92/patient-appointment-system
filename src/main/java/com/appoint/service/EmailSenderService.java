package com.appoint.service;

import com.appoint.entity.EmailBody;

import jakarta.mail.MessagingException;

public interface EmailSenderService {
	
	Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException;
	

}
