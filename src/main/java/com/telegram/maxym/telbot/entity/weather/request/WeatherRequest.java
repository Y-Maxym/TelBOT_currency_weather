package com.telegram.maxym.telbot.entity.weather.request;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class WeatherRequest {

    private String city;
    private String lang;
    private String days;

    private WeatherRequestCommand command;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (city != null && !city.isEmpty()) {
            stringBuilder.append("&").append("q=").append(city);
        }

        if (lang != null && !lang.isEmpty()) {
            stringBuilder.append("&").append("lang=").append(lang);
        }

        if (days != null && !days.isEmpty()) {
            stringBuilder.append("&").append("days=").append(days);
        }

        return stringBuilder.toString();
    }
}
