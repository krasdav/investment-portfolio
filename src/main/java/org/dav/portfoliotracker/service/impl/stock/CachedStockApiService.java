package org.dav.portfoliotracker.service.impl.stock;

import org.dav.portfoliotracker.service.StockApiService;
import org.dav.portfoliotracker.datacache.CacheStore;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service("cachedStockApiService")
public class CachedStockApiService implements StockApiService {

    private final StockApiService stockApiService;
    private final CacheStore<StockCached> stockCache;

    @Autowired
    public CachedStockApiService(@Qualifier("yahooApiService") StockApiService stockApiService, CacheStore<StockCached> stockCache) {
        this.stockApiService = stockApiService;
        this.stockCache = stockCache;
    }

    @Override
    public StockCached findStock(String ticker) throws IOException, StockNotFoundException {
        StockCached stockCached = stockCache.get(ticker);
        if (stockCached == null) {
            stockCached = stockApiService.findStock(ticker);
            stockCache.add(ticker, stockCached);
        }
        return stockCached;
    }

    @Override
    public BigDecimal findPrice(StockCached stockCached) throws IOException, StockNotFoundException {
        return getPrice(stockCached.getTicker());
    }

    @Override
    public BigDecimal findPrice(String ticker) throws IOException, StockNotFoundException {
        return getPrice(ticker);
    }

    @Override
    public BigDecimal getPrice(String ticker) throws IOException, StockNotFoundException {
        return findStock(ticker).getCurrentPrice();
    }
}
