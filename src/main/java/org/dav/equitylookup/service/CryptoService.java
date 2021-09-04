package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Coin;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.cache.CoinInfo;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.CoinDTO;

import java.util.List;

public interface CryptoService {

    CoinInfo getCoinInfo(String symbol);

    String getCoinPrice(String symbol);

    Coin obtainCoin(String symbol, User user);

    List<CoinDTO> getCoinDTO(Portfolio portfolio);

}
