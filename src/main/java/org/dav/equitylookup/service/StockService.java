package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface StockService{
    void saveStock(Stock stock);
    Stock getStockById(long id);
    void deleteStockById(long id);
    void addStock(Stock stock, User user) throws IOException;
    BigDecimal updateCurrentStockPrice(Stock stock) throws IOException;
    User findFirstUser();
}
