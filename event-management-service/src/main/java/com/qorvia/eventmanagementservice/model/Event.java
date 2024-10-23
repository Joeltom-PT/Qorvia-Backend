package com.qorvia.eventmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {

    @Id
    private UUID id;

    private String name;

    private EventCategory category;

    private String description;

    private EventType eventType;

    private String date;

    private String time;

    private int duration;

    private String image;

    private String contactInfo;

    private String additionalNotes;

    private BigDecimal price;

    private boolean ticketsFree;

    private Long organizerId;

    private EventPublicationStatus publicationStatus;

    private EventStatus status;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    private List<UUID> participantIds;
}
