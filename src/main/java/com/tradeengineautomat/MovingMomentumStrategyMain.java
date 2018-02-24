package com.tradeengineautomat;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.tradeengineautomat.bot.market.model.Interval;
import com.tradeengineautomat.bot.market.trading.*;

public class MovingMomentumStrategyMain {

    public static void main(String[] args) {
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
        TrendIndicator myTrendIndicator = new MovingMomentumIndicator();
        CurrencyCandlestickListener btc_usdt_CandlestickListener = new BasicCurrencyCandlestickListener(
                client, Interval.ONE_MINUTE, "btcusdt", myTrendIndicator);
    }
}
