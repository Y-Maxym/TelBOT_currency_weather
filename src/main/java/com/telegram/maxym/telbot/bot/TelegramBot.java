package com.telegram.maxym.telbot.bot;

import com.telegram.maxym.telbot.entity.Currency;
import com.telegram.maxym.telbot.service.CurrencyService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String START = "/start";
    private final String USD = "/usd";
    private final String EUR = "/eur";

    private final CurrencyService currencyService;
    private final MessageSource messageSource;

    @Value("${bot.username}")
    private String username;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken,
                       CurrencyService currencyService,
                       MessageSource messageSource) {
        super(botToken);
        this.currencyService = currencyService;
        this.messageSource = messageSource;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        distribute(message.getText(), chatId);
    }

    @SneakyThrows
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        execute(message);
    }

    private void distribute(String receivedMessage, String chatId) {
        switch (receivedMessage) {
            case START -> startMessage(chatId);
            case USD -> currencyMessage(chatId, "USD");
            case EUR -> currencyMessage(chatId, "EUR");
        }
    }

    private void startMessage(String chatId) {
        String message = messageSource.getMessage("start", null, LocaleContextHolder.getLocale());
        sendMessage(chatId, message);
    }

    private void currencyMessage(String chatId, String requestedCurrency) {
        Currency currency = currencyService.getCurrency(requestedCurrency);
        String message = messageSource.getMessage("currency",
                new Object[]{
                        currency.getBaseCurrency(),
                        currency.getCurrency(),
                        currency.getBuy(),
                        currency.getSale()
                }, LocaleContextHolder.getLocale());
        sendMessage(chatId, message);
    }
}
