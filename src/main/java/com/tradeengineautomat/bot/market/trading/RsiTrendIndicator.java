package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;
import eu.verdelhan.ta4j.BaseTick;
import eu.verdelhan.ta4j.BaseTimeSeries;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.RSIIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.function.Predicate;

public class RsiTrendIndicator implements TrendIndicator {
    private int numberOfTimeFrames;
    private TimeSeries timeSeries;
    private RSIIndicator rsiIndicator;
    private int lowerBound;
    private int higherBound;
    private int index;

    private Predicate<Integer> invalidRange = (number) -> number > 0 && 100 < number;


    public RsiTrendIndicator(int numberOfTimeFrames, int lowerBound, int higherBound) {
        if (invalidRange.test(lowerBound) || invalidRange.test(higherBound)) {
            throw new IllegalArgumentException("Invalid Range of bounds");
        }

        this.numberOfTimeFrames = numberOfTimeFrames;
        this.timeSeries = new BaseTimeSeries();
        this.rsiIndicator = new RSIIndicator(new ClosePriceIndicator(timeSeries), numberOfTimeFrames);
        this.lowerBound = lowerBound;
        this.higherBound = higherBound;
    }

    @Override
    public void tryDetermineTrend(Candlestick candlestick) {
        BaseTick tick = new BaseTick(
                ZonedDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getEventTime() + 300), ZoneOffset.UTC),
                candlestick.getOpen().doubleValue(),
                candlestick.getHigh().doubleValue(),
                candlestick.getLow().doubleValue(),
                candlestick.getClose().doubleValue(),
                candlestick.getVolume().doubleValue());

        System.out.println(index + "\t\t" + tick);
        timeSeries.addTick(tick);
        if (index >= numberOfTimeFrames) {
            Decimal rsiValue = rsiIndicator.getValue(index);
            determineTrend(rsiValue.toDouble());
        }
        index++;
    }

    private void determineTrend(double rsiValue) {
        System.out.println("RSI: " + rsiValue);
    }

    @Override
    public void emitTrendChanged(String symbol, Trend trend) {

    }
}