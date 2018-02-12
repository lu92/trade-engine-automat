package com.tradeengineautomat.bot.market.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreatedOrderResponse {
    private String symbol;
    private Long orderId;
    private String clientOrderId;
    private Long transactTime;
}
