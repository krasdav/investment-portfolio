package org.dav.equitylookup.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.ShareDTO;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockApiService;
import org.dav.equitylookup.service.StockService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockApiService stockApiService;

    private final CacheStore<Stock> stockCache;

    private final PortfolioService portfolioService;

    public List<Stock> updateStockPrices(Portfolio portfolio) throws IOException, PortfolioNotFoundException {
        //BigDecimal portfolioValue = new BigDecimal("0");
        List<Stock> stocksUpdated = new ArrayList<>();
        for ( Share share : portfolio.getShares()){
            Stock stock = stockCache.get(share.getTicker());
            if( stock == null) {
                BigDecimal currentPrice = stockApiService.findPrice(share.getTicker());
                stock = stockCache.add(share.getTicker(), new Stock(share.getTicker(),share.getCompany(),currentPrice));
            }
        //    portfolioValue = portfolioValue.add(stock.getCurrentPrice());
            stocksUpdated.add(stock);
        }
        //portfolioService.updatePortfolioValue(portfolio.getName(),portfolioValue);
        return stocksUpdated;
    }

    public void updateStockPrices(Share... shares) throws IOException {
        for ( Share share : shares){
            Stock stock = stockCache.get(share.getTicker());
            if( stock == null) {
                BigDecimal currentPrice = stockApiService.findPrice(share.getTicker());
                stockCache.add(share.getTicker(), new Stock(share.getTicker(),share.getCompany(),currentPrice));
            }
        }
    }

    @Override
    public void setFinancialDetails(List<ShareDTO> shareDTOS) throws IOException {
        for( ShareDTO shareDTO : shareDTOS){
            BigDecimal currentPrice = stockCache.get(shareDTO.getTicker()).getCurrentPrice();
            if( currentPrice == null){
                currentPrice = stockApiService.findPrice(shareDTO.getTicker());
                stockCache.add(shareDTO.getTicker(), new Stock(shareDTO.getTicker(),shareDTO.getCompany(),currentPrice));
            }
            shareDTO.setCurrentPrice(currentPrice);
            shareDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(shareDTO.getBoughtPrice(),currentPrice));
            shareDTO.setValueChange(FinancialAnalysis.getValueChange(shareDTO.getBoughtPrice(),currentPrice));
        }
    }

    @Override
    public Share obtainShare(String ticker, User user) throws IOException {
        Stock stock = stockCache.get(ticker);
        if(stock == null){
            stock = stockApiService.findStock(ticker);
            stockCache.add(ticker,stock);
        }
        return new Share(stock.getCurrentPrice(),stock,user);
    }

    @Override
    public void cacheStocks(Stock... stocks) {
        for(Stock stock : stocks){
            stockCache.add(stock.getTicker(),stock);
        }
    }

    @Override
    public Stock getStock(String ticker) throws IOException {
        Stock stock = stockCache.get(ticker);
        if(stock == null ){
            stock = stockApiService.findStock(ticker);
            stockCache.add(ticker,stock);
        }
        return stock;
    }

    @Override
    public List<Stock> getTopStocks() throws IOException {
        List<String> topStocksTicker = List.of("INTC","GOOG","AAPL","CSCO");
        List<Stock> topStocks = new ArrayList<>();
        for(String ticker : topStocksTicker){
            Stock stock = stockCache.get(ticker);
            if(stock == null ){
                stock = stockApiService.findStock(ticker);
                stockCache.add(ticker,stock);
            }
            topStocks.add(stock);
        }
        return topStocks;
    }

}
