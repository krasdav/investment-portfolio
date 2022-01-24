package org.dav.portfoliotracker.service;

import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.model.cache.CryptoCached;
import org.dav.portfoliotracker.model.dto.CryptoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CryptoService {

    CryptoCached getCoinInfo(String symbol) throws CryptoNotFoundException;

    BigDecimal getCoinPrice(String symbol) throws CryptoNotFoundException;

    List<CryptoDTO> getAnalyzedCryptoDTOS(Portfolio portfolio) throws CryptoNotFoundException;

    void setDynamicData(List<CryptoDTO> cryptoDTOS) throws CryptoNotFoundException;

    List<CryptoDTO> getAndRemoveSoldOutCryptos(List<CryptoDTO> cryptoDTOS);
}
