package com.telegram.maxym.telbot.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.maxym.telbot.entity.Currency;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ExchangeClient {

    private final ObjectMapper objectMapper;
    @Value("${exchange.url}")
    private String exchangeUrl;

    public Currency getCurrency(String currency) {
        String response = WebClient.create(exchangeUrl)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractCurrency(response, currency);
    }

    @SneakyThrows
    private Currency extractCurrency(String response, String currency) {
        Currency[] currencyEntities = objectMapper.readValue(response, Currency[].class);
        return Arrays.stream(currencyEntities)
                .filter(currencyEntity -> currencyEntity.getCurrency().equals(currency))
                .findFirst()
                .orElse(null);
    }
}
