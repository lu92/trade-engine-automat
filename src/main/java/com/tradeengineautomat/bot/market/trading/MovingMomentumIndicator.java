package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.Candlestick;
import eu.verdelhan.ta4j.*;
import eu.verdelhan.ta4j.indicators.EMAIndicator;
import eu.verdelhan.ta4j.indicators.MACDIndicator;
import eu.verdelhan.ta4j.indicators.StochasticOscillatorKIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class MovingMomentumIndicator implements TrendIndicator {

    private TimeSeries timeSeries;
    private ClosePriceIndicator closePrice;
    private int index;
    private EMAIndicator shortEma;
    private EMAIndicator longEma;
    private StochasticOscillatorKIndicator stochasticOscillK;
    private MACDIndicator macd;
    private EMAIndicator emaMacd;
    private Rule entryRule;
    private Rule exitRule;
    private Strategy movingMomentumStrategy;

    public MovingMomentumIndicator() {
        this.timeSeries = new BaseTimeSeries();
        this.closePrice = new ClosePriceIndicator(timeSeries);
        this.shortEma = new EMAIndicator(closePrice, 9);
        this.longEma = new EMAIndicator(closePrice, 26);

        this.stochasticOscillK = new StochasticOscillatorKIndicator(timeSeries, 14);

        this.macd = new MACDIndicator(closePrice, 9, 26);
        this.emaMacd = new EMAIndicator(macd, 18);

        // Entry rule
        this.entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedDownIndicatorRule(stochasticOscillK, Decimal.valueOf(20))) // Signal 1
                .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

        // Exit rule
        this.exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80))) // Signal 1
                .and(new UnderIndicatorRule(macd, emaMacd)); // Signal 2

        // Running the strategy
        movingMomentumStrategy = new BaseStrategy(entryRule, exitRule);
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
            System.out.print("UNSTABLE: " + movingMomentumStrategy.isUnstableAt(index) + "\t");
            System.out.print("MACD: " + macd.getValue(index) + "\t\t");
            System.out.println(index + "\t\t" + tick);

            if (movingMomentumStrategy.shouldEnter(index)) {
                System.out.println("SHOULD ENTER");
            }

            if (movingMomentumStrategy.shouldExit(index)) {
                System.out.println("SHOULD EXIT");
            }
            index++;
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void emitTrendChanged(String symbol, Trend trend) {

    }
}
