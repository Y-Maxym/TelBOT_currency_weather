package com.telegram.maxym.telbot.service.message;

import com.telegram.maxym.telbot.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherMessageService implements MessageService {

    private final RequestService requestService;
    private final MessageSource messageSource;

    @Override
    public List<String> getMessages(String receivedMessage) {
        return switch (receivedMessage) {
            case "/weather" -> startMessages();
            case "/send" -> requestService.sendRequest();
            default -> requestService.sendCommand(receivedMessage);
        };
    }

    private List<String> startMessages() {
        requestService.newRequest();
        return List.of(
                messageSource.getMessage("weather.start", null, LocaleContextHolder.getLocale())
        );
    }
}
