package com.telegram.maxym.telbot.bot;

import com.telegram.maxym.telbot.service.UpdateService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final UpdateService updateService;

    @Value("${bot.username}")
    private String username;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken, UpdateService updateService) {
        super(botToken);
        this.updateService = updateService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<SendMessage> message = updateService.distribute(update);
        message.forEach(this::sendMessage);
    }

    @SneakyThrows
    private void sendMessage(SendMessage message) {
        execute(message);
    }
}
