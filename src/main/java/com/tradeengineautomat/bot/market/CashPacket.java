package com.tradeengineautomat.bot.market;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CashPacket {
    private String symbol;
    private BigDecimal purchasePrice;
    private BigDecimal quantity;
    private OrderType origin;
    private OrderType purpose;
}