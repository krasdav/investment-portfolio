package org.dav.equitylookup.service.impl.stock;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.model.dto.GroupedStockSharesDTO;
import org.dav.equitylookup.model.dto.StockShareDTO;
import org.dav.equitylookup.service.StockApiService;
import org.dav.equitylookup.service.StockService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockApiService cachedStockApiService;

    private final CacheStore<Stock> stockCache;

    private final ModelMapper modelMapper;

    @Override
    public List<Stock> updateStockPrices(Portfolio portfolio) throws IOException {
        return updateStockPrices(portfolio.getStockShares());
    }

    @Override
    public List<Stock> updateStockPrices(List<StockShare> stockShares) throws IOException {
        List<Stock> stocksUpdated = new ArrayList<>();
        for (StockShare stockShare : stockShares) {
            Stock stock = cachedStockApiService.findStock(stockShare.getTicker());
            stocksUpdated.add(stock);
        }
        return stocksUpdated;
    }

    @Override
    public StockShare obtainShare(String ticker, User user) throws IOException {
        Stock stock = cachedStockApiService.findStock(ticker);
        return new StockShare(stock.getCurrentPrice(), stock, user);
    }

    @Override
    public void cacheStocks(Stock... stocks) {
        for (Stock stock : stocks) {
            stockCache.add(stock.getTicker(), stock);
        }
    }

    @Override
    public Stock getStock(String ticker) throws IOException {
        return cachedStockApiService.findStock(ticker);
    }

    @Override
    public List<Stock> getTopStocks() throws IOException {
        List<String> topStocksTicker = List.of("INTC", "GOOG", "AAPL", "CSCO");
        List<Stock> topStocks = new ArrayList<>();
        for (String ticker : topStocksTicker) {
            Stock stock = stockCache.get(ticker);
            if (stock == null) {
                stock = cachedStockApiService.findStock(ticker);
                stockCache.add(ticker, stock);
            }
            topStocks.add(stock);
        }
        return topStocks;
    }

    @Override
    public List<GroupedStockSharesDTO> obtainGroupedAnalyzedDTO(Portfolio portfolio) throws IOException {
        List<GroupedStockSharesDTO> groupedStockSharesDTOS = new ArrayList<>();
        List<String> stocksGrouped = new ArrayList<>();

        //iterate over all shares and group them in the list by ticker
        for( StockShare stockShare : portfolio.getStockShares()){
            if( !stocksGrouped.contains(stockShare.getTicker())){
                groupedStockSharesDTOS.add(new GroupedStockSharesDTO(stockShare));
                stocksGrouped.add(stockShare.getTicker());
                continue;
            }
            for ( GroupedStockSharesDTO groupedStockSharesDTO : groupedStockSharesDTOS){
                if ( groupedStockSharesDTO.getTicker().equals(stockShare.getTicker())){
                    groupedStockSharesDTO.addToPurchasePrice(stockShare.getBoughtPrice());
                    groupedStockSharesDTO.incrementAmount();
                }
            }

        }

        analyze(groupedStockSharesDTOS);
        return groupedStockSharesDTOS;
    }

    @Override
    public void analyze(List<GroupedStockSharesDTO> groupedStockShareDTOS) throws IOException {
        for (GroupedStockSharesDTO groupedStockSharesDTO : groupedStockShareDTOS) {
            BigDecimal currentPrice = cachedStockApiService.findPrice(groupedStockSharesDTO.getTicker());
            BigDecimal holdings = currentPrice.multiply(new BigDecimal(groupedStockSharesDTO.getAmount()));
            groupedStockSharesDTO.setCurrentPrice(currentPrice);
            groupedStockSharesDTO.setHoldings(holdings);
            groupedStockSharesDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(groupedStockSharesDTO.getTotalPurchasePrice(), holdings));
            groupedStockSharesDTO.setValueChange(FinancialAnalysis.getValueChange(groupedStockSharesDTO.getTotalPurchasePrice(), holdings));
        }
    }
}
