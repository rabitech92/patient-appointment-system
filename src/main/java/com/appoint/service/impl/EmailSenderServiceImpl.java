package com.appoint.service.impl;

import com.appoint.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.appoint.entity.EmailBody;

import jakarta.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
	

	private static JavaMailSender javaMailSender;

	@Override
	public Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("rabiulnewsinfo@gmail.com");
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setText(emailBody.getEmailBody());
		simpleMailMessage.setSubject(emailBody.getEmailSubject());
		
		javaMailSender.send(simpleMailMessage);
		
		
		return true;
	
	}

}
