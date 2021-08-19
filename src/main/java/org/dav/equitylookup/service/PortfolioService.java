package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;

import java.math.BigDecimal;
import java.util.List;

public interface PortfolioService {
    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;

    public void addShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    public void removeShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    public void updatePortfolioValue(String portfolioName, BigDecimal portfolioValueUpdated) throws PortfolioNotFoundException;

    public Share getShareById(long id, String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException;

    public void removeShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException;

}
