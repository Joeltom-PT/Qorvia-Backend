package com.qorvia.eventmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "offline_events")
public class OfflineEvent {

    @Id
    private UUID id;

    private UUID eventId;

    private List<TicketType> ticketTypes;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TicketType {
        private String type;
        private int quantity;
        private BigDecimal price;
        private BigDecimal discount;
    }
}
