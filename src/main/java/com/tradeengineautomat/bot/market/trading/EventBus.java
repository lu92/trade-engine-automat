package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;

import java.util.LinkedList;
import java.util.List;

public final class EventBus {
    private List<CurrencyCandlestickListener> listeners = new LinkedList<>();

    public void post(Candlestick candlestick) {
        listeners.stream()
                .filter(listener -> listener.applies(candlestick.getSymbol()))
                .forEach(listener -> listener.onPriceChanged(candlestick));
    }

    public void register(CurrencyCandlestickListener currencyCandlestickListener) {
        listeners.add(currencyCandlestickListener);
    }
}
