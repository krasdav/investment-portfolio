package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.dav.equitylookup.model.dto.CryptoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CryptoService {

    CryptoCached getCoinInfo(String symbol) throws CryptoNotFoundException;

    BigDecimal getCoinPrice(String symbol) throws CryptoNotFoundException;

    List<CryptoDTO> getAnalyzedCryptoDTOS(Portfolio portfolio) throws CryptoNotFoundException;

    void setDynamicData(List<CryptoDTO> cryptoDTOS) throws CryptoNotFoundException;

    List<CryptoDTO> getAndRemoveSoldOutCryptos(List<CryptoDTO> cryptoDTOS);
}
