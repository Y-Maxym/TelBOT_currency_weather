package com.telegram.maxym.telbot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Currency {

    @JsonProperty("base_ccy")
    private String baseCurrency;

    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("buy")
    private Double buy;

    @JsonProperty("sale")
    private Double sale;
}
