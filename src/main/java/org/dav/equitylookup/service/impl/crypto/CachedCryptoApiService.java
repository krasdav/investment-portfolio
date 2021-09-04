package org.dav.equitylookup.service.impl.crypto;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.cache.CoinInfo;
import org.dav.equitylookup.service.CryptoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("cachedCryptoApiService")
public class CachedCryptoApiService implements CryptoApiService {

    private final CryptoApiService cryptoApiService;
    private final CacheStore<CoinInfo> coinCache;

    @Autowired
    public CachedCryptoApiService(@Qualifier("binanceApiService") CryptoApiService cryptoApiService, CacheStore<CoinInfo> coinCache) {
        this.cryptoApiService = cryptoApiService;
        this.coinCache = coinCache;
    }

    @Override
    public CoinInfo getCoinInfo(String symbol) {
        System.out.println("COIN CACHE" + coinCache);
        CoinInfo coinInfo = coinCache.get(symbol);
        if (coinInfo == null) {
            System.out.println("I AM HERE I AM HERE");
            coinInfo = cryptoApiService.getCoinInfo(symbol);
            coinCache.add(symbol, coinInfo);
        }
        return coinInfo;
    }
}
