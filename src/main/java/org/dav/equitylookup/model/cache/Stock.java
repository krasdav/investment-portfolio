package org.dav.equitylookup.model.cache;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Stock {

    private String ticker;
    private String company;
    private BigDecimal currentPrice;

    public Stock() {
    }

    public Stock(String ticker, String company) {
        this.ticker = ticker;
        this.company = company;
    }

    public Stock(String ticker, String company, BigDecimal currentPrice) {
        this.ticker = ticker;
        this.company = company;
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return ticker.equals(stock.ticker) && company.equals(stock.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, company);
    }
}
