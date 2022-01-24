package org.dav.portfoliotracker.service.impl.crypto;

import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.model.cache.CryptoCached;
import org.dav.portfoliotracker.service.CryptoApiService;
import org.dav.portfoliotracker.datacache.CacheStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("cachedCryptoApiService")
public class CachedCryptoApiService implements CryptoApiService {

    private final CryptoApiService cryptoApiService;
    private final CacheStore<CryptoCached> coinCache;

    @Autowired
    public CachedCryptoApiService(@Qualifier("binanceApiService") CryptoApiService cryptoApiService, CacheStore<CryptoCached> coinCache) {
        this.cryptoApiService = cryptoApiService;
        this.coinCache = coinCache;
    }

    @Override
    public CryptoCached getCrypto(String symbol) throws CryptoNotFoundException {
        CryptoCached cryptoCached = coinCache.get(symbol);
        if (cryptoCached == null) {
            cryptoCached = cryptoApiService.getCrypto(symbol);
            coinCache.add(symbol, cryptoCached);
        }
        return cryptoCached;
    }
}
