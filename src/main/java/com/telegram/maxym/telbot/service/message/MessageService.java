package com.telegram.maxym.telbot.service.message;


import java.util.List;

public interface MessageService {

    List<String> getMessages(String receivedMessage);
}
