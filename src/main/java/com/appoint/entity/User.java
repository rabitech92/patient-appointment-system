package com.appoint.entity;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    private ObjectId id;
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter valid mobile number")
    private String mobileNo;
}
