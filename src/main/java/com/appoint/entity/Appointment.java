package com.appoint.entity;


import java.time.LocalDateTime;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@ToString
public class Appointment {
	
	@Id
	private ObjectId appointmentId;
	

	private Patient patient;
	// Appointement default time will be 60 mins from appoaintment start time.
	private LocalDateTime appointmentDateAndTime;

	private Doctor doctor;

	@JsonIgnore
	private Review review;
	@Override
	public int hashCode() {
		return Objects.hash(appointmentId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(appointmentId, other.appointmentId);
	}
}

