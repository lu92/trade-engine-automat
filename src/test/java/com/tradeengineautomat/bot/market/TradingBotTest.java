package com.tradeengineautomat.bot.market;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.tradeengineautomat.bot.market.WalletConfigBuilder.addAmount;

@RunWith(MockitoJUnitRunner.class)
public class TradingBotTest {


    private TradingBot tradingBot;

    @Test
    public void test() {



        BinanceMarket binanceMarket = new BinanceMarket(credentials);


        TradingBot tradingBot = new TradingBotBuilder()
                .wallet()
                .allowedCurrencies("BTC", "ETH", "WABI", "USDT")
                .amountOfAllowedCryptoCash(
                        addAmount("WABI", 10.0)).end()
                .market(binanceMarket)
                .build();
        System.out.println(tradingBot);
    }


}