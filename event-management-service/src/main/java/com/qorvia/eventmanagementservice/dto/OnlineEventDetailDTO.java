package com.qorvia.eventmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineEventDetailDTO {
    private String name;
    private String category;
    private String description;
    private String bannerImg;
    private String type;
    private List<EventDateTimeDTO> eventDateTime;
    private List<EventParticipantDTO> participants;
    private List<EventGuideDTO> guides;
}
