package org.dav.equitylookup.service.impl;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.service.StockApiService;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@Service("yahooApiService")
public class YahooApiService implements StockApiService {

    public Stock findStock(String ticker) throws IOException {
        yahoofinance.Stock stock = YahooFinance.get(ticker);
        BigDecimal currentPrice = stock.getQuote().getPrice();
        String company = stock.getName();
        return new Stock(ticker,company,currentPrice );
    }

    public BigDecimal findPrice(Stock stock) throws IOException {
        return getPrice(stock.getTicker());
    }

    public BigDecimal findPrice(String ticker) throws IOException  {
        return getPrice(ticker);
    }

    public BigDecimal getPrice(String ticker) throws IOException {
        return YahooFinance.get(ticker).getQuote().getPrice();
    }

}
