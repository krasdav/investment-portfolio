package org.dav.equitylookup.service;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;
    private final StockSearchService stockSearchService;

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

    public void addStock(Stock stock, User user) throws IOException {
        user.addStock(stock);
        stock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker())));
        stock.setUser(user);
        saveStock(stock);
    }

    public BigDecimal updateCurrentStockPrice(Stock stock) throws IOException {
        BigDecimal currentPrice = stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker()));
        stock.setCurrentPrice(currentPrice);
        return currentPrice;
    }

    @Override
    public User findFirstUser() {
        return stockRepository.findFirstUser();
    }


}
