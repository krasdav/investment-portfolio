package org.dav.portfoliotracker.service;

import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.exceptions.PortfolioNotFoundException;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.model.TransactionRecord;
import org.dav.portfoliotracker.model.dto.PortfolioDTO;

import java.io.IOException;
import java.util.List;

public interface PortfolioService {

    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;

    PortfolioDTO obtainAnalyzedDTO(Portfolio portfolio) throws IOException, StockNotFoundException, CryptoNotFoundException;

    void addStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, IOException, StockNotFoundException;

    void removeStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, StockNotFoundException;

    void addCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, CryptoNotFoundException;

    void removeCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, CryptoNotFoundException;

}
