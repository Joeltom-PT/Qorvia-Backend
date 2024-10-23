package com.qorvia.accountservice.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class UserActivityController {


    private final SimpMessagingTemplate messagingTemplate;

    public UserActivityController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyUserBlockStatus(String email) {
        log.info("User is bocking by the email : {}", email);
        messagingTemplate.convertAndSend("/topic/userStatus", email);
    }

}
//