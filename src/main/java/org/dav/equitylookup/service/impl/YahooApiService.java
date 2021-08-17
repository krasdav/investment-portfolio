package org.dav.equitylookup.service.impl;

import lombok.AllArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class YahooApiService {

    public Stock findStock(String ticker) {
        try {
            return new Stock(ticker,YahooFinance.get(ticker).getQuote().getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal findPrice(Stock stock) throws IOException {
        return YahooFinance.get(stock.getTicker()).getQuote().getPrice();
    }

    public BigDecimal findPrice(String ticker) throws IOException {
        return YahooFinance.get(ticker).getQuote().getPrice();
    }

}
