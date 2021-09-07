package org.dav.equitylookup.service.impl.stock;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.model.dto.ShareDTO;
import org.dav.equitylookup.service.StockApiService;
import org.dav.equitylookup.service.StockService;
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

    @Override
    public List<Stock> updateStockPrices(Portfolio portfolio) throws IOException {
        return updateStockPrices(portfolio.getShares());
    }

    @Override
    public List<Stock> updateStockPrices(List<Share> shares) throws IOException {
        List<Stock> stocksUpdated = new ArrayList<>();
        for (Share share : shares) {
            Stock stock = cachedStockApiService.findStock(share.getTicker());
            stocksUpdated.add(stock);
        }
        return stocksUpdated;
    }

    public void addAnalysisDetails(List<ShareDTO> shareDTOS) throws IOException {
        for (ShareDTO shareDTO : shareDTOS) {
            BigDecimal currentPrice = cachedStockApiService.findPrice(shareDTO.getTicker());
            shareDTO.setCurrentPrice(currentPrice);
            shareDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(shareDTO.getBoughtPrice(), currentPrice));
            shareDTO.setValueChange(FinancialAnalysis.getValueChange(shareDTO.getBoughtPrice(), currentPrice));
        }
    }

    @Override
    public Share obtainShare(String ticker, User user) throws IOException {
        Stock stock = cachedStockApiService.findStock(ticker);
        return new Share(stock.getCurrentPrice(), stock, user);
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

}
