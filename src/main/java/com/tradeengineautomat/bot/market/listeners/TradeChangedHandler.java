package com.tradeengineautomat.bot.market.listeners;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.domain.event.AggTradeEvent;

public interface TradeChangedHandler extends BinanceApiCallback<AggTradeEvent> {

}
