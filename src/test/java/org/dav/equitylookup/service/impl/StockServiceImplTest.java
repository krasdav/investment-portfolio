package org.dav.equitylookup.service.impl;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockApiService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.impl.stock.StockServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    @Qualifier("CachedStockApiService")
    private StockApiService stockApiService;

    @Mock
    private CacheStore<Stock> stockCache;

    private StockService stockService;
    private Portfolio portfolio;

    @BeforeEach
    void setup() {
        stockService = new StockServiceImpl(stockApiService, stockCache);
        User user = new User("user1");
        portfolio = new Portfolio("Test_Portfolio", user);
        Share apple = new Share(new BigDecimal("100"), new Stock("AAPL", "Apple"), user);
        Share google = new Share(new BigDecimal("150"), new Stock("GOOG", "Google"), user);
        Share intel = new Share(new BigDecimal("200"), new Stock("INTC", "Intel"), user);
        portfolio.addShare(apple);
        portfolio.addShare(google);
        portfolio.addShare(intel);
    }

    @Test
    void updateStockByPortfolio() throws IOException {
        when(stockApiService.findStock(any())).thenReturn(
                new Stock("AAPL", "Apple", new BigDecimal("100")),
                new Stock("GOOG", "Google", new BigDecimal("200")),
                new Stock("INTC", "Intel", new BigDecimal("200")));

        List<Stock> stocks = stockService.updateStockPrices(portfolio);
        Assertions.assertEquals(3, stocks.size());
        Assertions.assertEquals(new BigDecimal(100), stocks.get(0).getCurrentPrice());
        Assertions.assertEquals(new BigDecimal(200), stocks.get(1).getCurrentPrice());
        Assertions.assertEquals(new BigDecimal(200), stocks.get(2).getCurrentPrice());
    }

    @Test
    void testUpdateStockPrices() throws IOException {
        when(stockApiService.findStock(any())).thenReturn(
                new Stock("AAPL", "Apple", new BigDecimal("100")),
                new Stock("GOOG", "Google", new BigDecimal("200")),
                new Stock("INTC", "Intel", new BigDecimal("200")));

        List<Stock> stocks = stockService.updateStockPrices(portfolio.getShares());
        Assertions.assertEquals(3, stocks.size());
        Assertions.assertEquals(new BigDecimal(100), stocks.get(0).getCurrentPrice());
        Assertions.assertEquals(new BigDecimal(200), stocks.get(1).getCurrentPrice());
        Assertions.assertEquals(new BigDecimal(200), stocks.get(2).getCurrentPrice());
    }

    @Test
    void setFinancialDetails() {
    }

    @Test
    void obtainShare() {
    }

    @Test
    void cacheStocks() {
    }

    @Test
    void getStock() {
    }

    @Test
    void getTopStocks() {
    }
}