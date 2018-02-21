package com.tradeengineautomat.bot.market.trading;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TrendListener {
    private Map<String, Trend> trendsMap = new ConcurrentHashMap<>();

    public void onTrendChanged(String symbol, Trend trend) {
        trendsMap.putIfAbsent(symbol, trend);

        switch (trend) {
            case RISING:
                break;

            case HORIZONTAL:
                break;

            case FAILING:
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
