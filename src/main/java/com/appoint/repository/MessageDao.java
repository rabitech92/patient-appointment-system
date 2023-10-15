package com.appoint.repository;


import com.appoint.entity.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageDao extends MongoRepository<Message, ObjectId> {

}
