package org.dav.portfoliotracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.model.Cryptocurrency;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.repository.PortfolioRepository;
import org.dav.portfoliotracker.service.CryptoApiService;
import org.dav.portfoliotracker.service.PortfolioService;
import org.dav.portfoliotracker.service.StockApiService;
import org.dav.portfoliotracker.exceptions.PortfolioNotFoundException;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.model.Stock;
import org.dav.portfoliotracker.model.TransactionRecord;
import org.dav.portfoliotracker.model.dto.PortfolioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    private final StockApiService cachedStockApiService;

    private final CryptoApiService cachedCryptoApiService;

    private final ModelMapper modelMapper;

    @Override
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio getPortfolioById(long id) {
        return portfolioRepository.getById(id);
    }

    @Override
    public Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException {
        Optional<Portfolio> matched = getAllPortfolios().stream().filter(p -> p.getName().equals(name)).findFirst();
        if (matched.isPresent()) {
            return matched.get();
        } else {
            throw new PortfolioNotFoundException("Portfolio Not Found");
        }
    }

    @Transactional
    public void addStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, IOException, StockNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        String ticker = transactionRecord.getAssetSymbol();
        Stock stock = portfolio.getStockByTicker(ticker);
        if (stock == null) {
            stock = new Stock(transactionRecord.getAssetSymbol(), cachedStockApiService.findStock(ticker).getCompany(), portfolio);
        }
        stock.addTransaction(transactionRecord);
        portfolio.addStock(stock);
    }

    @Transactional
    public void removeStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, StockNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        String ticker = transactionRecord.getAssetSymbol();
        Stock stock = portfolio.getStockByTicker(ticker);
        if (stock == null) {
            throw new StockNotFoundException("Stock not found in portfolio.");
        }
        stock.addTransaction(transactionRecord);
    }

    @Transactional
    public void addCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, CryptoNotFoundException {
        cachedCryptoApiService.getCrypto(transactionRecord.getAssetSymbol());
        Portfolio portfolio = getPortfolioByName(portfolioName);
        Cryptocurrency crypto = portfolio.getCryptoCurrencyBySymbol(transactionRecord.getAssetSymbol());
        if (crypto == null) {
            crypto = new Cryptocurrency(transactionRecord.getAssetSymbol(), portfolio);
        }
        crypto.addTransaction(transactionRecord);
        portfolio.addCryptocurrency(crypto);
    }

    @Transactional
    public void removeCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, CryptoNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        Cryptocurrency crypto = portfolio.getCryptoCurrencyBySymbol(transactionRecord.getAssetSymbol());
        if (crypto == null) {
            throw new CryptoNotFoundException("Cryptocurrency not found in portfolio");
        }
        crypto.addTransaction(transactionRecord);
    }

    @Override
    public PortfolioDTO obtainAnalyzedDTO(Portfolio portfolio) throws IOException, StockNotFoundException, CryptoNotFoundException {
        PortfolioDTO portfolioDTO = modelMapper.map(portfolio, PortfolioDTO.class);
        BigDecimal portfolioValue = new BigDecimal("0");
        for (Stock stock : portfolioDTO.getStocks()) {
            BigDecimal currentPrice = cachedStockApiService.findPrice(stock.getTicker());
            portfolioValue = portfolioValue.add(currentPrice);
        }

        for (Cryptocurrency crypto : portfolioDTO.getCryptocurrencies()) {
            BigDecimal currentPrice = cachedCryptoApiService.getCrypto(crypto.getSymbol()).getCurrentPrice().multiply(BigDecimal.valueOf(crypto.getFraction()));
            portfolioValue = portfolioValue.add(currentPrice);
        }

        portfolioDTO.setPortfolioValue(portfolioValue.setScale(2, RoundingMode.HALF_EVEN));
        return portfolioDTO;
    }
}
