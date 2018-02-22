package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.CryptoCurrencyMarket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TrendListener {
    private Map<String, Trend> trendsMap = new ConcurrentHashMap<>();
    private CryptoCurrencyMarket cryptoCurrencyMarket;

    public TrendListener(CryptoCurrencyMarket cryptoCurrencyMarket) {
        this.cryptoCurrencyMarket = cryptoCurrencyMarket;
    }

    public void onTrendChanged(String symbol, Trend trend) {
        trendsMap.putIfAbsent(symbol, trend);

        switch (trend) {
            case RISING:
                System.out.println(symbol.toUpperCase() + "TREND CHANGED TO : " + Trend.RISING);
                break;

            case HORIZONTAL:
                System.out.println(symbol.toUpperCase() + "TREND CHANGED TO : " + Trend.HORIZONTAL);
                break;

            case FAILING:
                System.out.println(symbol.toUpperCase() + "TREND CHANGED TO : " + Trend.FAILING);
                break;

            case NOT_DETERMINED:
                break;
        }
    }

    public Trend getCurrentTrend(String symbol) {
        if (trendsMap.containsKey(symbol)) {
            return trendsMap.get(symbol);
        } else {
            throw new IllegalArgumentException("Trend for " + symbol + " is not determined!");
        }
    }
}
