package org.dav.equitylookup.service.impl.crypto;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.service.CryptoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("cachedCryptoApiService")
public class CachedCryptoApiService implements CryptoApiService {

    private final CryptoApiService cryptoApiService;
    private final CacheStore<Crypto> coinCache;

    @Autowired
    public CachedCryptoApiService(@Qualifier("binanceApiService") CryptoApiService cryptoApiService, CacheStore<Crypto> coinCache) {
        this.cryptoApiService = cryptoApiService;
        this.coinCache = coinCache;
    }

    @Override
    public Crypto getCrypto(String symbol) {
        Crypto crypto = coinCache.get(symbol);
        if (crypto == null) {
            crypto = cryptoApiService.getCrypto(symbol);
            coinCache.add(symbol, crypto);
        }
        return crypto;
    }
}
