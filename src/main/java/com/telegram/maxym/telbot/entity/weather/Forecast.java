package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class Forecast {
    private ForecastDay[] forecastday;
}
