package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.io.IOException;
import java.util.List;

public interface StockService {
    List<Stock> updateStockPrices(Portfolio portfolio) throws IOException, PortfolioNotFoundException;

    void updateStockPrices(Share... shares) throws IOException;

    Share obtainShare(String ticker, User user) throws IOException;

    void cacheStocks(Stock... stocks);

    List<Stock> getTopStocks() throws IOException;

    Stock getStock(String ticker) throws IOException ;
}
