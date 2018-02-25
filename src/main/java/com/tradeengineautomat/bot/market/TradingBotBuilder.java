package com.tradeengineautomat.bot.market;

import com.tradeengineautomat.bot.market.trading.WalletConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TradingBotBuilder {
    private CryptoCurrencyMarket market;
    private WalletConfigBuilder walletConfigBuilder = new WalletConfigBuilder(this);

    public WalletConfigBuilder wallet() {
        return walletConfigBuilder;
    }

    public TradingBotBuilder market(CryptoCurrencyMarket market) {
        this.market = market;
        return this;
    }

    public TradingBot build() {
        return new TradingBot(null, market, walletConfigBuilder.build());
    }
}

class WalletConfigBuilder {

    private WalletConfig walletConfig;
    private List<String> allowedCurrencies;
    private List<AllowedCash> allowedAmountList;
    private TradingBotBuilder builder;

    public WalletConfigBuilder(TradingBotBuilder tradingBotBuilder) {
        this.builder = tradingBotBuilder;
    }

    public WalletConfigBuilder allowedCurrencies(String ... currencies) {
        allowedCurrencies = Arrays.asList(currencies);
        return this;
    }

    public WalletConfigBuilder amountOfAllowedCryptoCash(AllowedCash ... allowedAmount) {
        this.allowedAmountList = Arrays.asList(allowedAmount);
        return this;
    }

    static AllowedCash addAmount(String currency, Double amount) {
        return new AllowedCash(currency, amount);
    }

    public TradingBotBuilder end() {
        Map<String, Double> amountOfAllowedCryptoCash = allowedAmountList.stream()
                .collect(Collectors.toMap(AllowedCash::getCurrency, AllowedCash::getAmount));
        this.walletConfig =  new WalletConfig(allowedCurrencies, amountOfAllowedCryptoCash);
        return builder;
    }

    public WalletConfig build() {
        return this.walletConfig;
    }
}

@Getter
@AllArgsConstructor
class AllowedCash {
    private String currency;
    private Double amount;
}
