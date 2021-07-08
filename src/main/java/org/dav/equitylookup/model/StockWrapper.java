package org.dav.equitylookup.model;

import lombok.Getter;
import yahoofinance.Stock;

import java.time.LocalDateTime;

@Getter
public class StockWrapper {

    private final Stock stock;
    private final LocalDateTime localDateTime;

    public StockWrapper(Stock stock){
        this.stock = stock;
        this.localDateTime = LocalDateTime.now();
    }
}
