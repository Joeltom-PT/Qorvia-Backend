package com.qorvia.eventmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "online_events")
public class OnlineEvent {

    @Id
    private UUID id;

    private UUID eventId;

    private BigDecimal price;

    private BigDecimal discount;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

}
