package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.TransactionRecord;
import org.dav.equitylookup.model.dto.PortfolioDTO;

import java.io.IOException;
import java.util.List;

public interface PortfolioService {

    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;

    PortfolioDTO obtainAnalyzedDTO(Portfolio portfolio) throws IOException;

    void addStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, IOException;

    void removeStock(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, StockNotFoundException;

    void addCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException;

    void removeCrypto(TransactionRecord transactionRecord, String portfolioName) throws PortfolioNotFoundException, CryptoNotFoundException;

}
