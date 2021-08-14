package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;

import java.util.List;

public interface PortfolioService {
    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;

    public void addShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    public void removeShare(Share share, String portfolioName) throws PortfolioNotFoundException;

    public void updatePortfolioValue(String portfolioName) throws PortfolioNotFoundException;

}
