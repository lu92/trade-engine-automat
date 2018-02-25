package com.tradeengineautomat.bot.market.trading;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class WalletConfig {
    private List<String> permittedCurrencies;
    private Map<String, Double> amountOfAllowedCryptoCash;

    public WalletConfig(List<String> permittedCurrencies, Map<String, Double> amountOfAllowedCryptoCash) {
        if (!permittedCurrencies.containsAll(amountOfAllowedCryptoCash.keySet())) {
            throw new IllegalArgumentException("Detected possibility of trading not permitted crypto currency!");
        }
        this.permittedCurrencies = permittedCurrencies;
        this.amountOfAllowedCryptoCash = amountOfAllowedCryptoCash;
    }
}
