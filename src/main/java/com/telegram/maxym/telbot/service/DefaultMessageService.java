package com.telegram.maxym.telbot.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultMessageService implements MessageService {
    @Override
    public String getMessage(String receivedMessage) {
        return "Unknown command";
    }
}
