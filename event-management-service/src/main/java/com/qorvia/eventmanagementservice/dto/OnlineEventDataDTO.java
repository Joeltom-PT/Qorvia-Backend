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
public class OnlineEventDataDTO {
    private OnlineEventDetailDTO detail;
    private OnlineEventTicketDTO ticket;
    private OnlineEventSettingDTO setting;
}
