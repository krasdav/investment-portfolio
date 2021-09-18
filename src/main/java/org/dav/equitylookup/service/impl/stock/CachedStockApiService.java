package org.dav.equitylookup.service.impl.stock;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.cache.StockCached;
import org.dav.equitylookup.service.StockApiService;
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
    public StockCached findStock(String ticker) throws IOException {
        StockCached stockCached = stockCache.get(ticker);
        if (stockCached == null) {
            stockCached = stockApiService.findStock(ticker);
            stockCache.add(ticker, stockCached);
        }
        return stockCached;
    }

    @Override
    public BigDecimal findPrice(StockCached stockCached) throws IOException {
        return getPrice(stockCached.getTicker());
    }

    @Override
    public BigDecimal findPrice(String ticker) throws IOException {
        return getPrice(ticker);
    }

    @Override
    public BigDecimal getPrice(String ticker) throws IOException {
        return findStock(ticker).getCurrentPrice();
    }
}
