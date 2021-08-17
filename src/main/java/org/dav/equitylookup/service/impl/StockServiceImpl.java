package org.dav.equitylookup.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.repository.StockRepository;
import org.dav.equitylookup.service.StockService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final YahooApiService yahooApiService;

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

    public void addStock(Stock stock){
        saveStock(stock);
    }

    public BigDecimal updateCurrentStockPrice(long id) throws IOException, StockNotFoundException {
        Optional<Stock> stock = stockRepository.findById(id);
        new BigDecimal("0");
        BigDecimal currentPrice;
        if( stock.isPresent()){
            currentPrice = yahooApiService.findPrice(stock.get());
            stock.get().setCurrentPrice(currentPrice);
        }else{
            throw new StockNotFoundException("Stock was not found");
        }
        return currentPrice;
    }

    public void updateStockPrices(List<Share> shares) throws IOException {
        for ( Share share : shares){
            Stock stock = stockRepository.getById(share.getStock().getId());
            BigDecimal currentPrice = yahooApiService.findPrice(stock);
            stock.setCurrentPrice(currentPrice);
        }
    }

    @Override
    public boolean stockExists(String ticker) {
        for ( Stock stock : stockRepository.findAll() ){
            if( stock.getTicker().equals(ticker)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        for( Stock stock : stockRepository.findAll()){
            if( stock.getTicker().equals(ticker)){
                return stock;
            }
        }
        return null;
    }

}
