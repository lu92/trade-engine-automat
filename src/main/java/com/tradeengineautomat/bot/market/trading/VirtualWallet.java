package com.tradeengineautomat.bot.market.trading;

import com.tradeengineautomat.bot.market.model.CurrencyBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class VirtualWallet {
    private Map<String, CurrencyBalance> balance;
}
