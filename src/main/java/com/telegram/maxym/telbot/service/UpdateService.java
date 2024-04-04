package com.telegram.maxym.telbot.service;

import com.telegram.maxym.telbot.service.factory.ServiceFactory;
import com.telegram.maxym.telbot.service.message.CurrencyMessageService;
import com.telegram.maxym.telbot.service.message.DefaultMessageService;
import com.telegram.maxym.telbot.service.message.MessageService;
import com.telegram.maxym.telbot.service.message.WeatherMessageService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

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

    public List<SendMessage> distribute(Update update) {
        Message message = update.getMessage();

        String receivedMessage = message.getText();
        String chatId = String.valueOf(message.getChatId());

        switch (receivedMessage) {
            case EXCHANGE -> setMessageService(serviceFactory.getMessageService(CurrencyMessageService.class));
            case WEATHER -> setMessageService(serviceFactory.getMessageService(WeatherMessageService.class));
        }

        List<String> messagesToSend = switch (receivedMessage) {
            case START -> startMessage();
            case HELP -> helpMessage();
            default -> messageService.getMessages(receivedMessage);
        };

        return messagesToSend.stream()
                .map(msg -> new SendMessage(chatId, msg))
                .toList();
    }

    private List<String> startMessage() {
        setMessageService(serviceFactory.getMessageService(DefaultMessageService.class));
        return List.of(
                messageSource.getMessage("start", null, LocaleContextHolder.getLocale())
        );
    }

    private List<String> helpMessage() {
        setMessageService(serviceFactory.getMessageService(DefaultMessageService.class));
        return List.of(
                messageSource.getMessage("help", null, LocaleContextHolder.getLocale())
        );
    }
}
