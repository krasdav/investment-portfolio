package org.dav.equitylookup.service.impl.stock;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.service.StockApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service("cachedStockApiService")
public class CachedStockApiService implements StockApiService {

    private final StockApiService stockApiService;
    private final CacheStore<Stock> stockCache;

    @Autowired
    public CachedStockApiService(@Qualifier("yahooApiService") StockApiService stockApiService, CacheStore<Stock> stockCache) {
        this.stockApiService = stockApiService;
        this.stockCache = stockCache;
    }

    @Override
    public Stock findStock(String ticker) throws IOException {
        Stock stock = stockCache.get(ticker);
        if (stock == null) {
            stock = stockApiService.findStock(ticker);
            stockCache.add(ticker, stock);
        }
        return stock;
    }

    @Override
    public BigDecimal findPrice(Stock stock) throws IOException {
        return getPrice(stock.getTicker());
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
