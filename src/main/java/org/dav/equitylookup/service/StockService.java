package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface StockService{
    void saveStock(Stock stock);
    Stock getStockById(long id);
    void deleteStockById(long id);
    void addStock(Stock stock) throws IOException;
    public BigDecimal updateCurrentStockPrice(long id) throws IOException, StockNotFoundException;
    void updateStockPrices(List<Share> shares) throws IOException;
    boolean stockExists(String ticker);
    Stock getStockByTicker(String ticker);
}
