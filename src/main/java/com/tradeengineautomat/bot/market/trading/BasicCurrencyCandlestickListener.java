package com.tradeengineautomat.bot.market.trading;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.exception.BinanceApiException;
import com.tradeengineautomat.bot.market.model.Candlestick;
import com.tradeengineautomat.bot.market.model.Interval;

import java.math.BigDecimal;

public class BasicCurrencyCandlestickListener implements CurrencyCandlestickListener {
    private BinanceApiWebSocketClient client;
    private String symbol;
    private TrendIndicator trendIndicator;

    public BasicCurrencyCandlestickListener(BinanceApiWebSocketClient client, Interval interval, String symbol, TrendIndicator trendIndicator) {
        this.client = client;
        this.symbol = symbol;
        this.trendIndicator = trendIndicator;
        CurrencyCandlestickListener currencyCandlestickListener = this;

        client.onCandlestickEvent(symbol, CandlestickInterval.valueOf(interval.getValue()), new BinanceApiCallback<CandlestickEvent>() {
            @Override
            public void onResponse(CandlestickEvent candlestickEvent) throws BinanceApiException {
                Candlestick candlestick = new Candlestick(
                        symbol,
                        candlestickEvent.getOpenTime(),
                        new BigDecimal(candlestickEvent.getOpen()),
                        new BigDecimal(candlestickEvent.getHigh()),
                        new BigDecimal(candlestickEvent.getLow()),
                        new BigDecimal(candlestickEvent.getClose()),
                        new BigDecimal(candlestickEvent.getVolume()),
                        candlestickEvent.getCloseTime(),
                        new BigDecimal(candlestickEvent.getQuoteAssetVolume()),
                        candlestickEvent.getNumberOfTrades());

                currencyCandlestickListener.onPriceChanged(candlestick);
            }
        });
    }

    @Override
    public boolean applies(String symbol) {
        return this.symbol.equalsIgnoreCase(symbol);
    }

    @Override
    public void onPriceChanged(Candlestick candlestick) {
        trendIndicator.tryDetermineTrend(candlestick);
    }
}
