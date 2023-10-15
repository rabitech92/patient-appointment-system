package com.appoint.entity;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
	
	@Id
	private ObjectId messageId;
	private String messageContent;
	private ObjectId sender;
	private ObjectId receiver;
	private Patient patient;
	private Doctor doctor;
	private LocalDateTime messageTimeAndDate;
	
}




