package com.telegram.maxym.telbot.service;

import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    String getMessage(String receivedMessage);
}
