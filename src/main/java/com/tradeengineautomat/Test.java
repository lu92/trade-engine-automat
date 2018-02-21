package com.tradeengineautomat;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.exception.BinanceApiException;
import com.tradeengineautomat.bot.market.Credentials;
import eu.verdelhan.ta4j.BaseTick;
import eu.verdelhan.ta4j.BaseTimeSeries;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.RSIIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Test {

    public static void main(String[] args) {


        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();

        TimeSeries timeSeries = new BaseTimeSeries();

        client.onCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, new BinanceApiCallback<CandlestickEvent>() {
            private int index = 0;
            private ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(timeSeries);

            int numberOfTimeFrames = 10;
            private RSIIndicator rsiIndicator = new RSIIndicator(closePriceIndicator, numberOfTimeFrames);

            @Override
            public void onResponse(CandlestickEvent candlestickEvent) throws BinanceApiException {
                BaseTick tick = new BaseTick(
                        ZonedDateTime.ofInstant(Instant.ofEpochMilli(candlestickEvent.getEventTime()), ZoneOffset.UTC),
                        candlestickEvent.getOpen(),
                        candlestickEvent.getHigh(),
                        candlestickEvent.getLow(),
                        candlestickEvent.getClose(),
                        candlestickEvent.getVolume());

                System.out.println(index + "\t\t" + tick);
                timeSeries.addTick(tick);
                if (index > numberOfTimeFrames) {
                    System.out.println("RSI: " + rsiIndicator.getValue(index));
                }
                index++;
            }
        });

    }
}
