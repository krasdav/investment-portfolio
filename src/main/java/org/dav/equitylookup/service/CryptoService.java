package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.dav.equitylookup.model.dto.CryptoDTO;
import org.dav.equitylookup.model.dto.StockDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CryptoService {

    CryptoCached getCoinInfo(String symbol);

    BigDecimal getCoinPrice(String symbol);

    List<CryptoDTO> getAnalyzedCryptoDTOS(Portfolio portfolio);

    void setDynamicData(List<CryptoDTO> cryptoDTOS);

    List<CryptoDTO> getAndRemoveSoldOutCryptos(List<CryptoDTO> cryptoDTOS);
}
