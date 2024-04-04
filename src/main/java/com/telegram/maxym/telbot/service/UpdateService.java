package com.telegram.maxym.telbot.service;

import com.telegram.maxym.telbot.service.factory.ServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Setter
public class UpdateService {

    private final ServiceFactory serviceFactory;
    private final MessageSource messageSource;

    private final String START = "/start";
    private final String HELP = "/help";
    private final String EXCHANGE = "/exchange";
    private final String WEATHER = "/weather";

    private MessageService messageService;

    public SendMessage distribute(Update update) {
        Message message = update.getMessage();

        String receivedMessage = message.getText();
        String chatId = String.valueOf(message.getChatId());

        switch (receivedMessage) {
            case EXCHANGE -> setMessageService(serviceFactory.getMessageService(CurrencyService.class));
//            case WEATHER -> setMessageService(serviceFactory.getMessageService(WeatherService.class));
        }

        String messageToSend = switch (receivedMessage) {
            case START -> startMessage();
            case HELP -> helpMessage();
            default -> messageService.getMessage(receivedMessage);
        };

        return new SendMessage(chatId, messageToSend);
    }

    private String startMessage() {
        setMessageService(serviceFactory.getMessageService(DefaultMessageService.class));
        return messageSource.getMessage("start", null, LocaleContextHolder.getLocale());
    }

    private String helpMessage() {
        setMessageService(serviceFactory.getMessageService(DefaultMessageService.class));
        return messageSource.getMessage("help", null, LocaleContextHolder.getLocale());
    }
}
