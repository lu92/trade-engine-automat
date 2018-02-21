package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;

public interface TrendIndicator {
    void tryDetermineTrend(Candlestick candlestick);
    void emitTrendChanged(String symbol, Trend trend);
}
