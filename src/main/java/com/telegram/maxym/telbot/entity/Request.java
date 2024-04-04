package com.telegram.maxym.telbot.entity;

import lombok.Data;

@Data
public class Request {

    private String city;
    private String lang;
    private String days;

    private String command;

    public void transfer(String param) {

    }
}
