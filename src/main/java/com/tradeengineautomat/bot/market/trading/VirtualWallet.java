package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.CurrencyBalance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualWallet {
    private Map<String, CurrencyBalance> balance = new ConcurrentHashMap<>();
}
