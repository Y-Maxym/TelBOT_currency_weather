package com.telegram.maxym.telbot.service;

import com.telegram.maxym.telbot.entity.weather.ForecastDay;
import com.telegram.maxym.telbot.entity.weather.Weather;
import com.telegram.maxym.telbot.entity.weather.request.WeatherRequest;
import com.telegram.maxym.telbot.service.client.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telegram.maxym.telbot.entity.weather.request.WeatherRequestCommand.*;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final MessageSource messageSource;
    private final WeatherClient client;

    private WeatherRequest weatherRequest;

    public List<String> sendCommand(String command) {
        return switch (command) {
            case "/city" -> cityCommand();
            case "/lang" -> langCommand();
            case "/days" -> daysCommand();
            default -> setParam(command);
        };
    }

    public List<String> cityCommand() {
        weatherRequest.setCommand(CITY);
        return List.of(
                messageSource.getMessage("weather.request.city", null, LocaleContextHolder.getLocale())
        );
    }

    private List<String> langCommand() {
        weatherRequest.setCommand(LANGUAGE);
        return List.of(
                messageSource.getMessage("weather.request.lang", null, LocaleContextHolder.getLocale())
        );
    }

    private List<String> daysCommand() {
        weatherRequest.setCommand(DAYS);
        return List.of(
                messageSource.getMessage("weather.request.days", null, LocaleContextHolder.getLocale())
        );
    }

    private List<String> setParam(String param) {
        switch (weatherRequest.getCommand()) {
            case CITY -> weatherRequest.setCity(param);
            case LANGUAGE -> weatherRequest.setLang(param);
            case DAYS -> weatherRequest.setDays(param);
        }
        return List.of(
                messageSource.getMessage("weather.request.next", null, LocaleContextHolder.getLocale())
        );
    }

    public List<String> sendRequest() {
        Weather weather = client.getWeather(weatherRequest.toString());
        List<String> messages = new ArrayList<>();
        getInfo(weather, messages);
        return messages;
    }

    private void getInfo(Weather weather, List<String> messages) {
        messages.add(
                messageSource.getMessage("weather.response",
                        new Object[]{
                                weather.getLocation().getName(),
                                weather.getLocation().getRegion(),
                                weather.getLocation().getCountry(),
                                weather.getLocation().getLat(),
                                weather.getLocation().getLon(),
                                weather.getLocation().getTz_id(),
                                weather.getLocation().getLocaltime(),
                                weather.getCurrent().getTemp_c(),
                                weather.getCurrent().getCondition().getText(),
                                weather.getCurrent().getWind_kph(),
                                weather.getCurrent().getPressure_mb(),
                                weather.getCurrent().getPrecip_mm(),
                                weather.getCurrent().getHumidity(),
                                weather.getCurrent().getCloud(),
                                weather.getCurrent().getFeelslike_c()
                        }, LocaleContextHolder.getLocale())
        );
        getForecastDays(weather, messages);
    }

    private void getForecastDays(Weather weather, List<String> messages) {
        Arrays.stream(weather.getForecast().getForecastday()).forEach(forecast -> {
            messages.add(
                    messageSource.getMessage("weather.forecast.response",
                            new Object[]{
                                    forecast.getDate(),
                                    forecast.getDay().getMaxtemp_c(),
                                    forecast.getDay().getMintemp_c(),
                                    forecast.getDay().getAvgtemp_c(),
                                    forecast.getDay().getMaxwind_kph(),
                                    forecast.getDay().getTotalprecip_mm(),
                                    forecast.getDay().getTotalsnow_cm(),
                                    forecast.getDay().getAvghumidity(),
                                    forecast.getDay().getDaily_will_it_rain() ? "Так" : "Ні",
                                    forecast.getDay().getDaily_chance_of_rain(),
                                    forecast.getDay().getDaily_will_it_snow() ? "Так" : "Ні",
                                    forecast.getDay().getDaily_chance_of_snow(),
                                    forecast.getDay().getCondition().getText(),
                            }, LocaleContextHolder.getLocale()
                    )
            );
            getForecastHours(forecast, messages);
        });
    }

    private void getForecastHours(ForecastDay forecast, List<String> messages) {
        Arrays.stream(forecast.getHour()).forEach(hour ->
                messages.add(
                        messageSource.getMessage("weather.forecast.hour.response",
                                new Object[]{
                                        hour.getTime(),
                                        hour.getTemp_c(),
                                        hour.getCondition().getText(),
                                        hour.getWind_kph(),
                                        hour.getPressure_mb(),
                                        hour.getPrecip_mm(),
                                        hour.getSnow_cm(),
                                        hour.getHumidity(),
                                        hour.getCloud(),
                                        hour.getFeelslike_c(),
                                        hour.getWill_it_rain() ? "Так" : "Ні",
                                        hour.getChance_of_rain(),
                                        hour.getWill_it_snow() ? "Так" : "Ні",
                                        hour.getChance_of_snow(),
                                }, LocaleContextHolder.getLocale()
                        )
                )
        );
    }

    public void newRequest() {
        weatherRequest = new WeatherRequest();
    }
}
