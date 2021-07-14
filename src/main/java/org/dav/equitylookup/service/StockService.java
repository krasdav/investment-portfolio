package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;

public interface StockService{
    void saveStock(Stock stock);
    Stock getStockById(long id);
    void deleteStockById(long id);
}
