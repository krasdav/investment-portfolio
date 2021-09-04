package org.dav.equitylookup.service;

import org.dav.equitylookup.model.cache.CoinInfo;

public interface CryptoApiService {
    CoinInfo getCoinInfo(String symbol);
}
