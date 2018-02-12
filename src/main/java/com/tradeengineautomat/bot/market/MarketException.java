package com.tradeengineautomat.bot.market;

public class MarketException extends RuntimeException {
    public MarketException(String message) {
        super(message);
    }

    public MarketException(Throwable cause) {
        super(cause);
    }
}
