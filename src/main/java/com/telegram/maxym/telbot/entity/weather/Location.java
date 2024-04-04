package com.telegram.maxym.telbot.entity.weather;

import lombok.Data;

@Data
public class Location {
    private String name;
    private String region;
    private String country;
    private Double lat;
    private Double lon;
    private String tz_id;
    private String localtime;
}
