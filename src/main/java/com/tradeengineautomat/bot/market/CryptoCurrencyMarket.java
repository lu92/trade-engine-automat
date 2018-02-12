package com.tradeengineautomat.bot.market;

import com.tradeengineautomat.bot.market.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CryptoCurrencyMarket {
    OrderBook getOrderBook(String symbol) throws MarketException;

    Map<String, BigDecimal> getExchangeRatesMap() throws MarketException;

    Map<String, BigDecimal> getAllLastPrices() throws MarketException;

    List<Order> getOpenedOrders(String symbol) throws MarketException;

    void cancelOrder(String symbol, long orderId) throws MarketException;

    CreatedOrderResponse sellOrderAtCustomPrice(String symbol, BigDecimal quantity, BigDecimal price) throws MarketException;

    CreatedOrderResponse sellOrderAtMarketPrice(String symbol, BigDecimal quantity) throws MarketException;

    CreatedOrderResponse buyOrder(String symbol, BigDecimal quantity, BigDecimal price) throws MarketException;

    Balance getBalance();

    CurrencyStatistics getPrice(String symbol) throws MarketException;

}