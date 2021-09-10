package org.dav.equitylookup.service.impl;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.repository.PortfolioRepository;
import org.dav.equitylookup.service.CryptoApiService;
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

    private final StockApiService cachedStockApiService;

    private final CryptoApiService cachedCryptoApiService;

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
    public StockShare getShareById(long id, String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException {
        Optional<StockShare> share = getPortfolioByName(portfolionName).getStockShares()
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        if (share.isPresent()) {
            return share.get();
        } else {
            throw new ShareNotFoundException("Share Not Found");
        }
    }

    @Override
    public CryptoShare getCryptoShareById(long id, String portfolionName) throws PortfolioNotFoundException, ShareNotFoundException {
        Optional<CryptoShare> coin = getPortfolioByName(portfolionName).getCryptocurrencies()
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        if (coin.isPresent()) {
            return coin.get();
        } else {
            throw new ShareNotFoundException("Coin Not Found");
        }
    }

    @Override
    public void addAnalysisDetails(PortfolioDTO portfolioDTO) throws IOException {
        BigDecimal portfolioValue = new BigDecimal("0");
        for (StockShare stockShare : portfolioDTO.getStockShares()) {
            BigDecimal currentPrice = cachedStockApiService.findPrice(stockShare.getTicker());
            portfolioValue = portfolioValue.add(currentPrice);
        }

        for (CryptoShare cryptoShare : portfolioDTO.getCryptocurrencies()) {
            String currentPrice = cachedCryptoApiService.getCrypto(cryptoShare.getSymbol()).getCurrentPrice();
            portfolioValue = portfolioValue.add(new BigDecimal(currentPrice));
        }

        portfolioDTO.setPortfolioValue(portfolioValue.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Transactional
    public void addShare(StockShare stockShare, String portfolioName) throws PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        portfolio.addShare(stockShare);
    }

    @Transactional
    public void removeShare(StockShare stockShare, String portfolioName) throws PortfolioNotFoundException {
        getPortfolioByName(portfolioName).removeShare(stockShare);
    }

    @Transactional
    public void removeShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException {
        StockShare stockShareToRemove = getShareById(id, portfolioName);
        removeShare(stockShareToRemove, portfolioName);
    }

    @Transactional
    public void addCryptoShare(CryptoShare cryptoShare, String portfolioName) throws PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByName(portfolioName);
        portfolio.addCoin(cryptoShare);
    }

    @Transactional
    public void removeCryptoShare(CryptoShare cryptoShare, String portfolioName) throws PortfolioNotFoundException {
        getPortfolioByName(portfolioName).removeCoin(cryptoShare);
    }

    @Transactional
    public void removeCryptoShareById(long id, String portfolioName) throws PortfolioNotFoundException, ShareNotFoundException {
        CryptoShare cryptoShareToRemove = getCryptoShareById(id, portfolioName);
        removeCryptoShare(cryptoShareToRemove, portfolioName);
    }
}
