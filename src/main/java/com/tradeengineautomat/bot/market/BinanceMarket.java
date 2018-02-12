package com.tradeengineautomat.bot.market;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import com.binance.api.client.exception.BinanceApiException;
import com.tradeengineautomat.bot.market.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinanceMarket implements CryptoCurrencyMarket {
    private BinanceApiRestClient client;

    public BinanceMarket(Credentials credentials) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(credentials.getApiKey(), credentials.getSecret());
        this.client = factory.newRestClient();
    }

    @Override
    public OrderBook getOrderBook(String symbol) throws MarketException {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getExchangeRatesMap() throws MarketException {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getAllLastPrices() throws MarketException {
        try {
            return client.getAllPrices().stream()
                    .collect(Collectors.toMap(TickerPrice::getSymbol, ticketPrice -> new BigDecimal(ticketPrice.getPrice())));
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

    @Override
    public List<Order> getOpenedOrders(String symbol) {
        List<com.binance.api.client.domain.account.Order> openOrders = client.getOpenOrders(new OrderRequest(symbol));
        return openOrders.stream().map(binanceOrder ->
                new Order(binanceOrder.getSymbol(), binanceOrder.getOrderId(), binanceOrder.getClientOrderId(),
                        new BigDecimal(binanceOrder.getPrice()), new BigDecimal(binanceOrder.getOrigQty()), new BigDecimal(binanceOrder.getExecutedQty()),
                        binanceOrder.getStatus(), binanceOrder.getTimeInForce(), binanceOrder.getType(), binanceOrder.getSide(),
                        new BigDecimal(binanceOrder.getStopPrice()), new BigDecimal(binanceOrder.getIcebergQty()))
        ).collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(String symbol, long orderId) {
        try {
            client.cancelOrder(new CancelOrderRequest(symbol, orderId));
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

    @Override
    public CreatedOrderResponse sellOrderAtCustomPrice(String symbol, BigDecimal quantity, BigDecimal price) throws MarketException {
        try {
            NewOrderResponse response = client.newOrder(NewOrder.limitSell(symbol, TimeInForce.GTC, quantity.toPlainString(), price.toPlainString()));
            return new CreatedOrderResponse(response.getSymbol(), response.getOrderId(), response.getClientOrderId(), response.getTransactTime());
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

    @Override
    public CreatedOrderResponse sellOrderAtMarketPrice(String symbol, BigDecimal quantity) throws MarketException {
        try {
            NewOrderResponse response = client.newOrder(NewOrder.marketSell(symbol, quantity.toPlainString()));
            return new CreatedOrderResponse(response.getSymbol(), response.getOrderId(), response.getClientOrderId(), response.getTransactTime());
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

    @Override
    public CreatedOrderResponse buyOrder(String symbol, BigDecimal quantity, BigDecimal price) throws MarketException {
        try {
            NewOrderResponse response = client.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, quantity.toPlainString(), price.toPlainString()));
            return new CreatedOrderResponse(response.getSymbol(), response.getOrderId(), response.getClientOrderId(), response.getTransactTime());
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

    @Override
    public Balance getBalance() {
        Map<String, CurrencyBalance> binanceBalance = client.getAccount().getBalances().stream().distinct()
                .map(asset -> new CurrencyBalance(asset.getAsset(), new BigDecimal(asset.getFree()), new BigDecimal(asset.getLocked())))
                .collect(Collectors.toMap(CurrencyBalance::getAsset, Function.identity()));
        return new Balance(binanceBalance);
    }

    @Override
    public CurrencyStatistics getPrice(String symbol) throws MarketException {
        try {
            TickerStatistics priceStatistics = client.get24HrPriceStatistics(symbol);
            System.out.println(priceStatistics);
            return new CurrencyStatistics();
        } catch (BinanceApiException e) {
            throw new MarketException(e);
        }
    }

}
