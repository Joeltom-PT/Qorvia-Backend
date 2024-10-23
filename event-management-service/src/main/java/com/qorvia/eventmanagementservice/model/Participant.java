package com.qorvia.eventmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "participants")
public class Participant {

    @Id
    private UUID id;

    private UUID eventId;

    private String role;

    private String name;

    private String about;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

}
