package org.dav.equitylookup.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.repository.PortfolioRepository;
import org.dav.equitylookup.service.PortfolioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

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
    public void updatePortfolioValue(String portfolioName) throws PortfolioNotFoundException {
        BigDecimal portfolioValueUpdated = new BigDecimal("0");
        Portfolio portfolio = getPortfolioByName(portfolioName);
        for (Share share : portfolio.getShares()) {
            portfolioValueUpdated = portfolioValueUpdated.add(share.getStock().getCurrentPrice());
        }
        portfolio.setPortfolioValue(portfolioValueUpdated);
    }

    @Override
    public Share getShareById(long id,String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException {
        Optional<Share> share = getPortfolioByName(portfolionName).getShares()
                .stream()
                .filter( s -> s.getId() == id)
                .findFirst();
        if( share.isPresent()){
            return share.get();
        }else{
            throw new ShareNotFoundException("Share Not Found");
        }
    }

    @Transactional
    public void addShare(Share share, String portfolioName) throws PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        portfolio.getShares().add(share);
        portfolio.addToPortfolioValue(share.getBoughtPrice());
    }

    @Transactional
    public void removeShare(Share share, String portfolioName) throws PortfolioNotFoundException {
        getPortfolioByName(portfolioName).removeShare(share);
    }

    @Transactional
    public void removeShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException {
        Share shareToRemove = getShareById(id,portfolioName);
        removeShare(shareToRemove,portfolioName);
    }
}
