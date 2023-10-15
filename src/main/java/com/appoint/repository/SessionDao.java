package com.appoint.repository;


import com.appoint.entity.CurrentSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SessionDao extends MongoRepository<CurrentSession, ObjectId> {
	
	public CurrentSession findByUuid(String uuid);
	
}
