package org.dav.equitylookup.service;

import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.model.dto.CryptoShareDTO;

import java.util.List;

public interface CryptoService {

    Crypto getCoinInfo(String symbol);

    String getCoinPrice(String symbol);

    CryptoShare obtainCryptoShare(double fraction, String symbol, User user);

    List<CryptoShareDTO> getCoinDTO(Portfolio portfolio);

}
