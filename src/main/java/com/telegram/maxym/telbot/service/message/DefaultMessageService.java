package com.telegram.maxym.telbot.service.message;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMessageService implements MessageService {
    @Override
    public List<String> getMessages(String receivedMessage) {
        return List.of("Unknown command");
    }
}
