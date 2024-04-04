package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class ForecastDay {
    private String date;
    private Day day;
    private Hour[] hour;
}
