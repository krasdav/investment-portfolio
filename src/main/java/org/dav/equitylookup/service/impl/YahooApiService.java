package org.dav.equitylookup.service.impl;

import lombok.AllArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class YahooApiService {

    public Stock findStock(String ticker) throws IOException {
        yahoofinance.Stock stock = YahooFinance.get(ticker);
        BigDecimal currentPrice = stock.getQuote().getPrice();
        String company = stock.getName();

        return new Stock(ticker,company,currentPrice );
    }

    public BigDecimal findPrice(Stock stock) throws IOException {
        return getPrice(stock.getTicker());
    }

    public BigDecimal findPrice(String ticker) throws IOException {
        return getPrice(ticker);
    }

    public BigDecimal getPrice(String ticker) throws IOException {
        return YahooFinance.get(ticker).getQuote().getPrice();
    }

}
