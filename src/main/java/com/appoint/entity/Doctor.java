package com.appoint.entity;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;


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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Doctor {
	
	@Id
	private ObjectId doctorId;
	@Pattern(regexp = "^[0-9]{10}$", message = "Please enter valid mobile number")
	private String mobileNo;
	private String password;
	private String name;
	private String specialty;
	private String location;
//	@Column(name = "insurance")
	private Boolean insuranceAcceptance;
	private String education;
	private String experience;
//	@OneToMany(cascade = CascadeType.ALL,mappedBy = "appointmentId")


	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();
	// put time only 24 hours formate
//	@Column(name = "from")
	private Integer appointmentFromTime;
	// put time only 24 hours formate
//	@Column(name = "to")
	private Integer appointmentToTime;
	// for checking this is doctor or patient
	private String type;


	@JsonIgnore
	private List<Review> listOfReviews = new ArrayList<>();
	private String doctorImg;
	private Boolean validDoctor = true;
	@JsonIgnore
	List<Message> listOfMessage = new ArrayList<>();
	
}






















