package com.tradeengineautomat.bot.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bid {
    private double price;
    private double quantity;
}
