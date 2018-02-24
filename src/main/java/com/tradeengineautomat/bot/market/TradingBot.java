package com.tradeengineautomat.bot.market;

import com.tradeengineautomat.bot.market.trading.TrendListener;
import com.tradeengineautomat.bot.market.trading.VirtualWallet;
import com.tradeengineautomat.bot.market.trading.strategies.TradingStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TradingBot {
    private Map<String, TradingStrategy> activeStrategies;
    private CryptoCurrencyMarket cryptoCurrencyMarket;
    private TrendListener trendListener;
    private VirtualWallet virtualWallet;
    private ProfitCalculator profitCalculator;
    private TradingHistory tradingHistory;


    public TradingBot(TrendListener trendListener, CryptoCurrencyMarket cryptoCurrencyMarket, VirtualWallet virtualWallet) {
        this.trendListener = trendListener;
        this.cryptoCurrencyMarket = cryptoCurrencyMarket;
        this.activeStrategies = new ConcurrentHashMap<>();
        this.virtualWallet = virtualWallet;
        this.profitCalculator = new ProfitCalculator();
        this.tradingHistory = new TradingHistory();
    }
}
