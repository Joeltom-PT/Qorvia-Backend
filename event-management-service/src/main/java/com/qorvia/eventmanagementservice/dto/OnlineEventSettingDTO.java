package com.qorvia.eventmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineEventSettingDTO {
    private boolean setting1;
    private boolean setting2;
    private boolean setting3;
    private String bookingStartDateAndTime;
    private String bookingEndDateAndTime;
    private String refundPolicy;
    private double refundAmount;
}
