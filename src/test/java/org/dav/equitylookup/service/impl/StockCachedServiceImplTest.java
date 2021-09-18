package org.dav.equitylookup.service.impl;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.cache.StockCached;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockApiService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.impl.stock.StockServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockCachedServiceImplTest {

    @Mock
    @Qualifier("CachedStockApiService")
    private StockApiService stockApiService;

    @Mock
    private CacheStore<StockCached> stockCache;

    @Mock
    private ModelMapper modelMapper;

    private StockService stockService;
    private Portfolio portfolio;

//    @BeforeEach
//    void setup() {
//        stockService = new StockServiceImpl(stockApiService, stockCache);
//        User user = new User("user1");
//        portfolio = new Portfolio("Test_Portfolio", user);
//        StockTransactionRecord apple = new StockTransactionRecord(new BigDecimal("100"), new StockCached("AAPL", "Apple"), user);
//        StockTransactionRecord google = new StockTransactionRecord(new BigDecimal("150"), new StockCached("GOOG", "Google"), user);
//        StockTransactionRecord intel = new StockTransactionRecord(new BigDecimal("200"), new StockCached("INTC", "Intel"), user);
//        portfolio.addShare(apple);
//        portfolio.addShare(google);
//        portfolio.addShare(intel);
//    }
//
//    @Test
//    void updateStockByPortfolio() throws IOException {
//        when(stockApiService.findStock(any())).thenReturn(
//                new StockCached("AAPL", "Apple", new BigDecimal("100")),
//                new StockCached("GOOG", "Google", new BigDecimal("200")),
//                new StockCached("INTC", "Intel", new BigDecimal("200")));
//
////        List<Stock> stocks = stockService.updateStockPrices(portfolio);
////        Assertions.assertEquals(3, stocks.size());
////        Assertions.assertEquals(new BigDecimal(100), stocks.get(0).getCurrentPrice());
////        Assertions.assertEquals(new BigDecimal(200), stocks.get(1).getCurrentPrice());
////        Assertions.assertEquals(new BigDecimal(200), stocks.get(2).getCurrentPrice());
//    }
//
//    @Test
//    void testUpdateStockPrices() throws IOException {
//        when(stockApiService.findStock(any())).thenReturn(
//                new StockCached("AAPL", "Apple", new BigDecimal("100")),
//                new StockCached("GOOG", "Google", new BigDecimal("200")),
//                new StockCached("INTC", "Intel", new BigDecimal("200")));
//
////        List<Stock> stocks = stockService.updateStockPrices(portfolio.getStockShares());
////        Assertions.assertEquals(3, stocks.size());
////        Assertions.assertEquals(new BigDecimal(100), stocks.get(0).getCurrentPrice());
////        Assertions.assertEquals(new BigDecimal(200), stocks.get(1).getCurrentPrice());
////        Assertions.assertEquals(new BigDecimal(200), stocks.get(2).getCurrentPrice());
//    }

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