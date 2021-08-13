package org.dav.equitylookup.service.implementation;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.repository.StockRepository;
import org.dav.equitylookup.service.StockService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

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

    public void addStock(Stock stock){
        saveStock(stock);
    }

    public BigDecimal updateCurrentStockPrice(Stock stock) throws IOException {
        BigDecimal currentPrice = stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker()));
        stock.setCurrentPrice(currentPrice);
        return currentPrice;
    }

    public void updateStockPrices(List<Share> shares) throws IOException {
        List<Stock> stocksToUpdate = shares.stream().map(Share::getStock).collect(Collectors.toList());
        for ( Stock stock : stocksToUpdate){
            BigDecimal currentPrice = stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker()));
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
