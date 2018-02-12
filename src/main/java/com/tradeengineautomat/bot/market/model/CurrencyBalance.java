package com.tradeengineautomat.bot.market.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CurrencyBalance {
    private String asset;
    private BigDecimal free;
    private BigDecimal locked;


    @Override
    public String toString() {
        return "CurrencyBalance{" +
                "asset='" + asset + '\'' +
                ", free=" + free.toPlainString() +
                ", locked=" + locked.toPlainString() +
                '}';
    }
}
