package com.tradeengineautomat.bot.market.model;

import lombok.Getter;

@Getter
public class CurrencyStatistics {
    private String priceChange;
    private String priceChangePercent;
    private String weightedAvgPrice;
    private String prevClosePrice;
    private String lastPrice;
    private String bidPrice;
    private String askPrice;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String volume;
    private long openTime;
    private long closeTime;
    private long firstId;
    private long lastId;
}
