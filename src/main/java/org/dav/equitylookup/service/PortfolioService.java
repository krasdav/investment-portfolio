package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.dto.PortfolioDTO;

import java.io.IOException;
import java.util.List;

public interface PortfolioService {

    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;

    void addShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    void removeShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    Share getShareById(long id, String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException;

    void removeShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException;

    void addAnalysisDetails(PortfolioDTO portfolioDTO) throws IOException;

    void addCryptoShare(CryptoShare cryptoShare, String portfolioName) throws PortfolioNotFoundException;

    void removeCryptoShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException;

    void removeCryptoShare(CryptoShare cryptoShare, String portfolioName) throws PortfolioNotFoundException;

    CryptoShare getCryptoShareById(long id, String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException;
}
