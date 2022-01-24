package org.dav.portfoliotracker.service.impl.crypto;

import lombok.RequiredArgsConstructor;
import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.model.cache.CryptoCached;
import org.dav.portfoliotracker.model.dto.CryptoDTO;
import org.dav.portfoliotracker.service.CryptoApiService;
import org.dav.portfoliotracker.service.CryptoService;
import org.dav.portfoliotracker.helper.FinancialAnalysis;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoApiService cachedCryptoApiService;

    private final ModelMapper modelMapper;

    @Override
    public CryptoCached getCoinInfo(String symbol) throws CryptoNotFoundException {
        return cachedCryptoApiService.getCrypto(symbol);
    }

    @Override
    public BigDecimal getCoinPrice(String symbol) throws CryptoNotFoundException {
        return getCoinInfo(symbol).getCurrentPrice();
    }

    @Override
    public List<CryptoDTO> getAnalyzedCryptoDTOS(Portfolio portfolio) throws CryptoNotFoundException {
        List<CryptoDTO> cryptoDTOS = modelMapper.map(portfolio.getCryptocurrencies(), new TypeToken<List<CryptoDTO>>() {
        }.getType());
        setDynamicData(cryptoDTOS);
        return cryptoDTOS;
    }

    @Override
    public void setDynamicData(List<CryptoDTO> cryptoDTOS) throws CryptoNotFoundException {
        for (CryptoDTO cryptoDTO : cryptoDTOS) {
            BigDecimal currentPrice = cachedCryptoApiService.getCrypto(cryptoDTO.getSymbol()).getCurrentPrice();
            BigDecimal holdingsMarketValue = currentPrice.multiply(BigDecimal.valueOf(cryptoDTO.getFraction()));
            cryptoDTO.setCurrentPrice(currentPrice);
            cryptoDTO.setMarketValue(holdingsMarketValue);
            cryptoDTO.setValueChange(FinancialAnalysis.getValueChange(
                    cryptoDTO.getTotalCostPrice(),
                    cryptoDTO.getTotalProceedsPrice(),
                    holdingsMarketValue));
            cryptoDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(
                    cryptoDTO.getTotalCostPrice(),
                    cryptoDTO.getTotalProceedsPrice(),
                    holdingsMarketValue,
                    currentPrice));
        }
    }

    @Override
    public List<CryptoDTO> getAndRemoveSoldOutCryptos(List<CryptoDTO> cryptoDTOS) {
        List<CryptoDTO> soldOutCrypto = new ArrayList<>();
        for (CryptoDTO cryptoDTO : cryptoDTOS) {
            if (cryptoDTO.getFraction() == 0) {
                soldOutCrypto.add(cryptoDTO);
            }
        }
        cryptoDTOS.removeIf(s -> s.getFraction() == 0);
        return soldOutCrypto;
    }

}
