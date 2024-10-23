package com.qorvia.eventmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineEventTicketDTO {
    private String type;
    private int totalTickets;
    private Double price;
    private String discountType;
    private Double discount;
}
