package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class Weather {

    private Location location;
    private CurrentWeather current;
    private Forecast forecast;
}
