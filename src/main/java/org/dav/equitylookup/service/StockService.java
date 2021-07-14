package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockService extends JpaRepository<Stock, Long> {
    void saveStock(User user);
    Stock getStockById(long id);
    void deleteStockById(long id);
}
