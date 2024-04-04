package com.telegram.maxym.telbot.service;

import com.telegram.maxym.telbot.entity.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CurrencyService implements MessageService {

    private final ExchangeClientService clientService;
    private final MessageSource messageSource;

    @Override
    @SuppressWarnings("all")
    public String getMessage(String receivedMessage) {
        return switch (receivedMessage) {
            case "/exchange" -> startMessage();
            default -> currencyMessage(receivedMessage);
        };
    }

    private String currencyMessage(String requestedCurrency) {
        Currency currency = clientService.getCurrency(requestedCurrency);
        if (isNull(currency)) return "Unknown currency";

        return messageSource.getMessage("currency.response",
                new Object[]{
                        currency.getBaseCurrency(),
                        currency.getCurrency(),
                        currency.getBuy(),
                        currency.getSale()
                }, LocaleContextHolder.getLocale());
    }

    private String startMessage() {
        return messageSource.getMessage("currency.start", null, LocaleContextHolder.getLocale());
    }
}
