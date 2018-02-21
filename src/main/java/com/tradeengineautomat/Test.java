package com.tradeengineautomat;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.tradeengineautomat.bot.market.Credentials;
import com.tradeengineautomat.bot.market.model.Interval;
import com.tradeengineautomat.bot.market.trading.*;

public class Test {

    public static void main(String[] args) {



        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
        TrendListener trendListener = new TrendListener();


        RsiTrendIndicator rsiTrendIndicator = new RsiTrendIndicator(30, 30, 70);
        CurrencyCandlestickListener btc_usdt_CandlestickListener = new BasicCurrencyCandlestickListener(client, Interval.ONE_MINUTE,"btcusdt", rsiTrendIndicator);

    }
}

