package com.telegram.maxym.telbot.service.message;

import com.telegram.maxym.telbot.entity.Currency;
import com.telegram.maxym.telbot.service.client.ExchangeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CurrencyMessageService implements MessageService {

    private final ExchangeClient clientService;
    private final MessageSource messageSource;

    @Override
    @SuppressWarnings("all")
    public List<String> getMessages(String receivedMessage) {
        return switch (receivedMessage) {
            case "/exchange" -> startMessages();
            default -> currencyMessages(receivedMessage);
        };
    }

    private List<String> currencyMessages(String requestedCurrency) {
        Currency currency = clientService.getCurrency(requestedCurrency);
        if (isNull(currency)) return List.of("Unknown currency");

        return List.of(
                messageSource.getMessage("currency.response",
                        new Object[]{
                                currency.getBaseCurrency(),
                                currency.getCurrency(),
                                currency.getBuy(),
                                currency.getSale()
                        }, LocaleContextHolder.getLocale())
        );
    }

    private List<String> startMessages() {
        return List.of(
                messageSource.getMessage("currency.start", null, LocaleContextHolder.getLocale())
        );
    }
}
