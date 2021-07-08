package org.dav.equitylookup.service;

import lombok.AllArgsConstructor;
import org.dav.equitylookup.model.StockWrapper;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class StockService {

    public StockWrapper findStock(String ticker) {

        try {
            return new StockWrapper(YahooFinance.get(ticker));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal findPrice(StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(true).getPrice();
    }

}
