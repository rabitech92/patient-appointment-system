package com.appoint.repository;



import com.appoint.entity.Appointment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentDao extends MongoRepository<Appointment, ObjectId> {

}
