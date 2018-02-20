package com.tradeengineautomat;

import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.exception.BinanceApiException;
import com.tradeengineautomat.bot.market.Credentials;
import com.tradeengineautomat.bot.market.listeners.AsyncTradeListener;
import com.tradeengineautomat.bot.market.listeners.TradeChangedHandler;
import eu.verdelhan.ta4j.indicators.SmoothedRSIIndicator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Credentials credentials = null;


        CurrencyListener btc_usdt_Listener = new CurrencyListener() {
            private SmoothedRSIIndicator rsi = new SmoothedRSIIndicator(null, 100);


            @Override
            public boolean applies(String symbol) {
                return "btcusdt".equalsIgnoreCase(symbol);
            }

            @Override
            public void onPriceChanged(CurencyOrder price) {
                System.out.println("RSI value: " +
                        rsi.getTimeSeries()


                );
            }
        };

        EventBus eventBus = new EventBus();
        eventBus.register(btc_usdt_Listener);


        AsyncTradeListener asyncTradeListener = new AsyncTradeListener(credentials, "btcusdt", new TradeChangedHandler() {

            @Override
            public void onResponse(AggTradeEvent aggTradeEvent) throws BinanceApiException {
                CurencyOrder curencyOrder = getPriceStatistic(aggTradeEvent);
                eventBus.post(curencyOrder);
            }


            private CurencyOrder getPriceStatistic(AggTradeEvent tradeEvent) {
                return new CurencyOrder(
                        tradeEvent.getSymbol(),
                        tradeEvent.getTradeTime(),
                        new BigDecimal(tradeEvent.getPrice()),
                        new BigDecimal(tradeEvent.getQuantity()));
            }
        });

        asyncTradeListener.start();
    }
}

final class EventBus {
    private List<CurrencyListener> listeners = new LinkedList<>();

    public void post(CurencyOrder curencyOrder) {
        listeners.stream()
                .filter(listener -> listener.applies(curencyOrder.getSymbol()))
                .forEach(listener -> listener.onPriceChanged(curencyOrder));
    }

    public void register(CurrencyListener currencyListener) {
        listeners.add(currencyListener);
    }
}

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
class CurencyOrder {
    private String symbol;
    private long time;
    private BigDecimal price;
    private BigDecimal quantity;
}


interface CurrencyListener {
    boolean applies(String symbol);
    void onPriceChanged(CurencyOrder newCurencyOrder);
}