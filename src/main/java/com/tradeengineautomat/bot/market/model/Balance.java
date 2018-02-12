package com.tradeengineautomat.bot.market.model;

import com.tradeengineautomat.bot.market.MarketException;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Balance {
    private Map<String, CurrencyBalance> balance;

    private final Predicate<CurrencyBalance> isFilled =
            currencyBalance -> 0 < currencyBalance.getFree().doubleValue() || 0 < currencyBalance.getLocked().doubleValue();

    public Balance getRealBalance() {
        Map<String, CurrencyBalance> realBalance = balance.entrySet().stream()
                .filter(entry -> isFilled.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Balance(realBalance);
    }

    public CurrencyBalance get(String currency) {
        if (!balance.containsKey(currency)) {
            throw new MarketException("balance does not contain " + currency);
        }
        return balance.get(currency);
    }

    @Override
    public String toString() {
        String body = getRealBalance().balance.values().stream().map(currencyBalance -> currencyBalance.toString()).collect(Collectors.joining("\n"));
        return "Balance{\n" + body + "\n}";
    }
}
