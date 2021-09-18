package org.dav.equitylookup.model.cache;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockCached {

    private final String ticker;
    private final String company;
    private final BigDecimal currentPrice;

    public StockCached(String ticker, String company) {
        this.ticker = ticker;
        this.company = company;
        this.currentPrice = new BigDecimal(100);
    }

    public StockCached(String ticker, String company, BigDecimal currentPrice) {
        this.ticker = ticker;
        this.company = company;
        this.currentPrice = currentPrice;
    }
}
