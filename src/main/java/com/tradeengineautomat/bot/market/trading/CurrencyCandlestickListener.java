package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;

public interface CurrencyCandlestickListener {
    boolean applies(String symbol);
    void onPriceChanged(Candlestick candlestick);
}
