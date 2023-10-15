package com.appoint.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
	
	@Id
	private ObjectId patientId;
	private String name;
	@Pattern(regexp = "^[0-9]{10}$", message = "Please enter valid mobile number")
	private String mobileNo;
	private String password;
	@Email(message = "Email should be a valid email")
	private String email;
	private String type;
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	@JsonIgnore
	List<Review> listReviews = new ArrayList<>();
	@JsonIgnore
	List<Message> listOfMessage = new ArrayList<>();
	

}

