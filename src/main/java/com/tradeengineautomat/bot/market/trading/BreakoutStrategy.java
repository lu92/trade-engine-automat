package com.tradeengineautomat.bot.market.trading;

import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.exception.BinanceApiException;
import com.tradeengineautomat.bot.market.CashPacket;
import com.tradeengineautomat.bot.market.Credentials;
import com.tradeengineautomat.bot.market.CryptoCurrencyMarket;
import com.tradeengineautomat.bot.market.listeners.AsyncTradeListener;
import com.tradeengineautomat.bot.market.listeners.TradeChangedHandler;

public class BreakoutStrategy implements TradingStrategy {
    private CryptoCurrencyMarket market;
    private CashPacket cashPacket;
    private Credentials credentials;

    public BreakoutStrategy(Credentials credentials, CryptoCurrencyMarket market, CashPacket cashPacket) {
        this.credentials = credentials;
        this.market = market;
        this.cashPacket = cashPacket;
    }

    @Override
    public void call() {
        new AsyncTradeListener(credentials, cashPacket.getSymbol(), new TradeChangedHandler() {

            @Override
            public void onResponse(AggTradeEvent response) throws BinanceApiException {

                displayTradeEvent(response);

                if (isProfitable(cashPacket, response)) {
                    market.sellOrderAtMarketPrice(cashPacket.getSymbol(), cashPacket.getQuantity());
                }
            }

            private boolean isProfitable(CashPacket cashPacket, AggTradeEvent tradeEvent) {
                return false;
            }

            private void displayTradeEvent(AggTradeEvent tradeEvent) {
                System.out.println("Trade event: {" +
                        "time: " + tradeEvent.getTradeTime() + ", " +
                        "eventType: " + tradeEvent.getEventType() + ", " +
                        "price:" + tradeEvent.getPrice() + ", " +
                        "quantity: " + tradeEvent.getQuantity() + "}");
            }

        }).start();
    }
}
