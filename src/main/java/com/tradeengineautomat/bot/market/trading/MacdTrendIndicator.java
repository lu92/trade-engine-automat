package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;
import eu.verdelhan.ta4j.BaseTick;
import eu.verdelhan.ta4j.BaseTimeSeries;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.MACDIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class MacdTrendIndicator implements TrendIndicator {

    private TrendListener trendListener;
    private TimeSeries timeSeries;
    private MACDIndicator macdIndicator;
    private int index;

    public MacdTrendIndicator(TrendListener trendListener) {
        this.trendListener = trendListener;
        this.timeSeries = new BaseTimeSeries();
        this.macdIndicator = new MACDIndicator(new ClosePriceIndicator(timeSeries), 12, 26);
    }

    @Override
    public void tryDetermineTrend(Candlestick candlestick) {
        try {
            Instant instant = Instant.ofEpochMilli(candlestick.getEventTime());
            BaseTick tick = new BaseTick(
                    ZonedDateTime.ofInstant(instant, ZoneOffset.UTC),
                    candlestick.getOpen().doubleValue(),
                    candlestick.getHigh().doubleValue(),
                    candlestick.getLow().doubleValue(),
                    candlestick.getClose().doubleValue(),
                    candlestick.getVolume().doubleValue());

            timeSeries.addTick(tick);
            System.out.println(index + "\t\t" + tick);
            System.out.println("MACD: " + macdIndicator.getValue(index));
            index++;
        } catch (IllegalArgumentException e) {
        }

    }

    @Override
    public void emitTrendChanged(String symbol, Trend trend) {

    }
}
