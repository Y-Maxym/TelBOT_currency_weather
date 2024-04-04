package com.telegram.maxym.telbot.service.client;

import com.telegram.maxym.telbot.entity.weather.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherClient {

    @Value("${weather.url}")
    private String baseUrl;

    @Value("${weather.key}")
    private String key;

    public Weather getWeather(String request) {

        return WebClient.create(baseUrl)
                .get()
                .uri("?key=" + key + request)
                .retrieve()
                .bodyToMono(Weather.class)
                .block();
    }
}
