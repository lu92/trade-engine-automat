package com.tradeengineautomat.bot.market.listeners;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.tradeengineautomat.bot.market.Credentials;

public class AsyncTradeListener {
    private BinanceApiWebSocketClient client;
    private String symbol;
    private TradeChangedHandler handler;

    public AsyncTradeListener(Credentials credentials, String symbol, TradeChangedHandler handler) {
        client = BinanceApiClientFactory.newInstance(
                credentials.getApiKey(),
                credentials.getSecret()
        ).newWebSocketClient();
        this.symbol = symbol;
        this.handler = handler;
    }

    public void start() {
        client.onAggTradeEvent(symbol, handler);
    }
}

