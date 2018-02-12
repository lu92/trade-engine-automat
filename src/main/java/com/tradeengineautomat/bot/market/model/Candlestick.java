package com.tradeengineautomat.bot.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Candlestick {
    public Long openTime;
    public BigDecimal open;
    public BigDecimal high;
    public BigDecimal low;
    public BigDecimal close;
    public BigDecimal volume;
    public Long closeTime;
    public BigDecimal quoteAssetVolume;
    public Long numberOfTrades;
    public BigDecimal takerBuyBaseAssetVolume;
    public BigDecimal takerBuyQuoteAssetVolume;
}
