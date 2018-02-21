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

        CurrencyCandlestickListener btc_usdt_CandlestickListener = new BasicCurrencyCandlestickListener(client, Interval.ONE_MIN,"btcusdt", null);



//        client.onCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, new BinanceApiCallback<CandlestickEvent>() {
//            private String symbol = "BTCUSDT";
//            private TimeSeries timeSeries = new BaseTimeSeries();
//            private int index = 0;
//            private ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(timeSeries);
//
//            int numberOfTimeFrames = 10;
//            private RSIIndicator rsiIndicator = new RSIIndicator(closePriceIndicator, numberOfTimeFrames);
//
//            @Override
//            public void onResponse(CandlestickEvent candlestickEvent) throws BinanceApiException {
//                Candlestick candlestick = new Candlestick(
//                        symbol,
//                        candlestickEvent.getOpenTime(),
//                        new BigDecimal(candlestickEvent.getOpen()),
//                        new BigDecimal(candlestickEvent.getHigh()),
//                        new BigDecimal(candlestickEvent.getLow()),
//                        new BigDecimal(candlestickEvent.getClose()),
//                        new BigDecimal(candlestickEvent.getVolume()),
//                        candlestickEvent.getCloseTime(),
//                        new BigDecimal(candlestickEvent.getQuoteAssetVolume()),
//                        candlestickEvent.getNumberOfTrades());
//
//
//                BaseTick tick = new BaseTick(
//                        ZonedDateTime.ofInstant(Instant.ofEpochMilli(candlestickEvent.getEventTime()), ZoneOffset.UTC),
//                        candlestickEvent.getOpen(),
//                        candlestickEvent.getHigh(),
//                        candlestickEvent.getLow(),
//                        candlestickEvent.getClose(),
//                        candlestickEvent.getVolume());
//
//                System.out.println(index + "\t\t" + tick);
//                timeSeries.addTick(tick);
//                if (index > numberOfTimeFrames) {
//                    System.out.println("RSI: " + rsiIndicator.getValue(index));
//                }
//                index++;
//            }
//        });
    }
}

