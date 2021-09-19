package org.dav.equitylookup.service.impl.crypto;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.dav.equitylookup.service.CryptoApiService;
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
