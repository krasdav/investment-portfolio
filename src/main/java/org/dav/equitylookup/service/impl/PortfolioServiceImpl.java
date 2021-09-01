package org.dav.equitylookup.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.repository.PortfolioRepository;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockApiService;
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

    private final CacheStore<Stock> stockCache;

    private final StockApiService cachedStockApiService;

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

    @Override
    public void addAnalysisDetails(PortfolioDTO portfolioDTO) throws IOException {
        BigDecimal portfolioValue = new BigDecimal("0");
        for ( Share share : portfolioDTO.getShares()){
            BigDecimal currentPrice = stockCache.get(share.getTicker()).getCurrentPrice();
            if( currentPrice == null){
                currentPrice = cachedStockApiService.findPrice(share.getTicker());
                stockCache.add(share.getTicker(), new Stock(share.getTicker(),share.getCompany(),currentPrice));
            }
            portfolioValue = portfolioValue.add(currentPrice);
        }
        portfolioDTO.setPortfolioValue(portfolioValue.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Transactional
    public void addShare(Share share, String portfolioName) throws PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        portfolio.getShares().add(share);
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
