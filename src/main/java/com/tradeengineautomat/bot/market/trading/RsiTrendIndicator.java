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
    private TrendListener trendListener;

    private Predicate<Integer> invalidRange = (number) -> number > 0 && 100 < number;


    public RsiTrendIndicator(TrendListener trendListener, int numberOfTimeFrames, int lowerBound, int higherBound) {
        if (invalidRange.test(lowerBound) || invalidRange.test(higherBound)) {
            throw new IllegalArgumentException("Invalid Range of bounds");
        }

        this.numberOfTimeFrames = numberOfTimeFrames;
        this.timeSeries = new BaseTimeSeries();
        this.rsiIndicator = new RSIIndicator(new ClosePriceIndicator(timeSeries), numberOfTimeFrames);
        this.lowerBound = lowerBound;
        this.higherBound = higherBound;
        this.trendListener = trendListener;
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
            if (index >= numberOfTimeFrames) {
                Decimal rsiValue = rsiIndicator.getValue(index);
                determineTrend(candlestick.getSymbol(), rsiValue.toDouble());
            }
            index++;
        } catch (IllegalArgumentException e) {
            System.out.println("error detected!");
        }
    }

    private void determineTrend(String symbol, double rsiValue) {
        System.out.println("RSI: " + rsiValue);
        if (rsiValue >= higherBound) {
            emitTrendChanged(symbol, Trend.FAILING);
        }

        if (rsiValue <= lowerBound) {
            emitTrendChanged(symbol, Trend.RISING);
        }
    }

    @Override
    public void emitTrendChanged(String symbol, Trend trend) {
        trendListener.onTrendChanged(symbol, trend);
    }
}
