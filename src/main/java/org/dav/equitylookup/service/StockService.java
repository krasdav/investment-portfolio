package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.io.IOException;
import java.math.BigDecimal;

public interface StockService{
    void saveStock(Stock stock);
    Stock getStockById(long id);
    void deleteStockById(long id);
    void addStock(Stock stock) throws IOException;
    BigDecimal updateCurrentStockPrice(Stock stock) throws IOException;
}
