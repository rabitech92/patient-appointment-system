package com.appoint.repository;



import com.appoint.entity.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewDao extends MongoRepository<Review, ObjectId> {

}
