package org.dav.portfoliotracker.service.impl;

import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.exceptions.PortfolioNotFoundException;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.model.*;
import org.dav.portfoliotracker.model.cache.CryptoCached;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.dav.portfoliotracker.model.enums.Operation;
import org.dav.portfoliotracker.repository.PortfolioRepository;
import org.dav.portfoliotracker.service.CryptoApiService;
import org.dav.portfoliotracker.service.StockApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private StockApiService cachedStockApiService;

    @Mock
    private CryptoApiService cachedCryptoApiService;

    @Mock
    private ModelMapper modelMapper;


    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    @Test
    void whenGetPortfolioByName_ThanReturnPortfolio() throws PortfolioNotFoundException {
        when(portfolioRepository.findAll()).thenReturn(List.of(
                new Portfolio("test", new User("TestUser")),
                new Portfolio("test2", new User("TestUser2"))));

        Portfolio portfolio = portfolioService.getPortfolioByName("test");
        assertNotNull(portfolio);
        assertEquals("test", portfolio.getName());
    }

    @Test
    void whenGetPortfolioByIncorrectName_ThanThrowException() {
        when(portfolioRepository.findAll()).thenReturn(List.of(
                new Portfolio("test", new User("TestUser")),
                new Portfolio("test2", new User("TestUser2"))));

        Exception exception = assertThrows(PortfolioNotFoundException.class, () ->
                portfolioService.getPortfolioByName("test3"));
        assertEquals("Portfolio Not Found", exception.getMessage());
    }

    @Test
    void whenAddStockCorrect_ThanStockIsAddedToPortfolio() throws PortfolioNotFoundException, StockNotFoundException, IOException {
        Portfolio portfolio = new Portfolio("test", new User("TestUser"));
        when(portfolioRepository.findAll()).thenReturn(List.of(
                portfolio,
                new Portfolio("test2", new User("TestUser2"))));
        when(cachedStockApiService.findStock("APPL")).thenReturn(new StockCached("APPL", "Apple"));

        portfolioService.addStock(new TransactionRecord(LocalDate.now(), "APPL", new BigDecimal(123), Operation.BUY, 123.0), "test");
        assertEquals(1, portfolio.getStocks().size());
        assertEquals("APPL", portfolio.getStocks().get(0).getTicker());
    }

    @Test
    void whenRemoveStockCorrect_ThanRecordIsAdded() throws StockNotFoundException, PortfolioNotFoundException {
        Portfolio portfolio = new Portfolio("test", new User("TestUser"));
        Stock stock = new Stock("APPL", "Apple", portfolio);
        portfolio.addStock(stock);
        stock.addTransaction(new TransactionRecord(LocalDate.now(), "APPL", new BigDecimal(5), Operation.BUY, 5));

        when(portfolioRepository.findAll()).thenReturn(List.of(
                portfolio,
                new Portfolio("test2", new User("TestUser2"))));

        portfolioService.removeStock(new TransactionRecord(LocalDate.now(), "APPL", new BigDecimal(5), Operation.SELL, 2), portfolio.getName());

        assertEquals(3, stock.getQuantity());
        assertEquals(2, stock.getTransactionRecords().size());
    }

    @Test
    void whenRemoveStockIncorrect_ThanThrowException() {
        Portfolio portfolio = new Portfolio("test", new User("TestUser"));
        when(portfolioRepository.findAll()).thenReturn(List.of(
                portfolio,
                new Portfolio("test2", new User("TestUser2"))));

        Exception exception = assertThrows(StockNotFoundException.class, () ->
                portfolioService.removeStock(new TransactionRecord(LocalDate.now(), "APPL", new BigDecimal(5), Operation.SELL, 2), portfolio.getName()));

        assertEquals("Stock not found in portfolio.", exception.getMessage());
    }


    @Test
    void whenAddCryptoCorrect_ThanCryptoIsAddedToPortfolio() throws PortfolioNotFoundException, CryptoNotFoundException {
        Portfolio portfolio = new Portfolio("test", new User("TestUser"));
        when(portfolioRepository.findAll()).thenReturn(List.of(
                portfolio,
                new Portfolio("test2", new User("TestUser2"))));
        when(cachedCryptoApiService.getCrypto("BTC")).thenReturn(new CryptoCached("BTC", new BigDecimal(25)));

        portfolioService.addCrypto(new TransactionRecord(LocalDate.now(), "BTC", new BigDecimal(25), Operation.BUY, 1), "test");
        assertEquals(1, portfolio.getCryptocurrencies().size());
    }


    @Test
    void whenRemoveCryptoCorrect_ThanRecordIsAdded() throws PortfolioNotFoundException, CryptoNotFoundException {
        Portfolio portfolio = new Portfolio("test", new User("TestUser"));
        Cryptocurrency cryptocurrency = new Cryptocurrency("BTC", portfolio);
        portfolio.addCryptocurrency(cryptocurrency);
        cryptocurrency.addTransaction(new TransactionRecord(LocalDate.now(), "BTC", new BigDecimal(25), Operation.BUY, 1));

        when(portfolioRepository.findAll()).thenReturn(List.of(
                portfolio,
                new Portfolio("test2", new User("TestUser2"))));

        portfolioService.removeCrypto(new TransactionRecord(LocalDate.now(), "BTC", new BigDecimal(25), Operation.SELL, 1), portfolio.getName());

        assertEquals(0, cryptocurrency.getFraction());
        assertEquals(2, cryptocurrency.getTransactionRecords().size());
    }


}