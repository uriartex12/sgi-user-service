package com.sgi.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user")
public class User {

    @Id
    private String id;
    @Indexed
    private String username;
    @JsonIgnore
    private String password;
    private String state;
    private String clientId;
    @CreatedDate
    @JsonIgnore
    private Instant createdDate;
    @LastModifiedDate
    @JsonIgnore
    private Instant updatedDate;
}
