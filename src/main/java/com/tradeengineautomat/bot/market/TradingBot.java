package com.tradeengineautomat.bot.market;

import com.tradeengineautomat.bot.market.model.Balance;
import com.tradeengineautomat.bot.market.model.CurrencyBalance;
import com.tradeengineautomat.bot.market.trading.TrendListener;
import com.tradeengineautomat.bot.market.trading.VirtualWallet;
import com.tradeengineautomat.bot.market.trading.WalletConfig;
import com.tradeengineautomat.bot.market.trading.strategies.TradingStrategy;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class TradingBot {
    private Map<String, TradingStrategy> activeStrategies;
    private CryptoCurrencyMarket cryptoCurrencyMarket;
    private TrendListener trendListener;
    private VirtualWallet virtualWallet;
    //    private ProfitCalculator profitCalculator;
    private TradingHistory tradingHistory;


    public TradingBot(TrendListener trendListener, CryptoCurrencyMarket cryptoCurrencyMarket, WalletConfig walletConfig) {
        this.trendListener = trendListener;
        this.cryptoCurrencyMarket = cryptoCurrencyMarket;
        this.activeStrategies = new ConcurrentHashMap<>();
//        this.profitCalculator = new ProfitCalculator();
        this.tradingHistory = new TradingHistory();
        this.virtualWallet = establishWallet(walletConfig);
    }

    private VirtualWallet establishWallet(WalletConfig walletConfig) {
        Balance realBalance = cryptoCurrencyMarket.getBalance().getRealBalance();
        boolean sufficientFounds = walletConfig.getAmountOfAllowedCryptoCash().entrySet().stream()
                .allMatch(entry -> {
                    String currency = entry.getKey();
                    Double amount = entry.getValue();
                    return realBalance.get(currency).getFree().doubleValue() > amount;
                });

        if (!sufficientFounds) {
            throw new IllegalArgumentException("Cannot establish virtual Wallet. Probably insufficient funds!");
        }

        VirtualWallet virtualWallet = new VirtualWallet(walletConfig.getAmountOfAllowedCryptoCash().entrySet().stream().collect(Collectors.toConcurrentMap(
                Map.Entry::getKey,
                entry -> {
                    String currency = entry.getKey();
                    Double amount = entry.getValue();
                    return new CurrencyBalance(currency, BigDecimal.valueOf(amount), BigDecimal.ZERO); })
        ));

        walletConfig.getPermittedCurrencies()
                .forEach(currency -> {
                    CurrencyBalance defaultBalance = new CurrencyBalance(currency, BigDecimal.ZERO, BigDecimal.ZERO);
                    virtualWallet.getBalance().putIfAbsent(currency, defaultBalance);
                });

        return virtualWallet;
    }
}
