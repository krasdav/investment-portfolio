package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock getStockById(long id) {
        Optional<Stock> optional = stockRepository.findById(id);
        Stock stock;
        if ( optional.isPresent()){
            stock = optional.get();
        }else{
            throw new RuntimeException("User not found");
        }
        return stock;
    }

    @Override
    public void deleteStockById(long id) {
        stockRepository.deleteById(id);
    }
}
