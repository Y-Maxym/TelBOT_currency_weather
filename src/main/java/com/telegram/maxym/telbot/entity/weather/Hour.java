package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class Hour {
    private String time;
    private Double temp_c;
    private Condition condition;
    private Double wind_kph;
    private Double pressure_mb;
    private Double precip_mm;
    private Double snow_cm;
    private Double humidity;
    private Double cloud;
    private Double feelslike_c;
    private Boolean will_it_rain;
    private Double chance_of_rain;
    private Boolean will_it_snow;
    private Double chance_of_snow;
}
