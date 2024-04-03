package com.telegram.maxym.telbot.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        String text = "Your message: %s".formatted(message.getText());
        sendMessage(chatId, text);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @SneakyThrows
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        execute(message);
    }
}
