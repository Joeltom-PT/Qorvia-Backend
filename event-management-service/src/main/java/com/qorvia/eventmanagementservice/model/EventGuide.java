package com.qorvia.eventmanagementservice.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class EventGuide {

    @Id
    private UUID id;

    private UUID eventId;

    private String icon_name;

    private String heading;

    private String content;
}
