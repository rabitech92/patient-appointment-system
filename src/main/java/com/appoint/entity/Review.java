package com.appoint.entity;


import java.util.Objects;


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
public class Review {
	
	@Id
	private ObjectId reviewId;
	private Patient patient;
	private Doctor doctor;
	private Appointment appointment;
	private String reviewContent;
	private float rating;

	@Override
	public int hashCode() {
		return Objects.hash(reviewId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(reviewId, other.reviewId);
	}
	
	

}






