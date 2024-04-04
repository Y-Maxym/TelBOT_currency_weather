package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class Day {
    private Double maxtemp_c;
    private Double mintemp_c;
    private Double avgtemp_c;
    private Double maxwind_kph;
    private Double totalprecip_mm;
    private Double totalsnow_cm;
    private Double avghumidity;
    private Boolean daily_will_it_rain;
    private Double daily_chance_of_rain;
    private Boolean daily_will_it_snow;
    private Double daily_chance_of_snow;
    private Condition condition;
}
