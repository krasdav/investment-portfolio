package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.model.Portfolio;

import java.util.List;

public interface PortfolioService {
    void savePortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(long id);

    Portfolio getPortfolioByName(String name) throws PortfolioNotFoundException;
}
