package com.appoint.repository;



import com.appoint.entity.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PatientRepository extends MongoRepository<Patient, ObjectId> {
	
	public Patient findByMobileNo(String mobileNo);
}
