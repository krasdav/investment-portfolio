package org.dav.equitylookup.service.implementation;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.repository.PortfolioRepository;
import org.dav.equitylookup.service.PortfolioService;
import org.springframework.stereotype.Service;

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
        Optional<Portfolio> matched = getAllPortfolios().stream().filter(p -> p.equals(name)).findFirst();
        if(matched.isPresent()){
            return matched.get();
        }else{
            throw new PortfolioNotFoundException("Portfolio Not Found");
        }
    }
}
