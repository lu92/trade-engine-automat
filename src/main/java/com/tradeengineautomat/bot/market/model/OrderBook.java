package com.tradeengineautomat.bot.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderBook {
    private long lastUpdateId;
    private List<Bid> bids;
    private List<Ask> asks;
}
