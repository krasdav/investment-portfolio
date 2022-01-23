package org.dav.equitylookup.service.impl.crypto;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.dav.equitylookup.service.CryptoApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CachedCryptoApiServiceTest {

    @Mock
    @Qualifier("binanceApiService")
    private CryptoApiService cryptoApiService;

    private final CacheStore<CryptoCached> coinCache = new CacheStore<>(90, TimeUnit.SECONDS);

    private CachedCryptoApiService cachedCryptoApiService;

    @BeforeEach
    void setup(){
        cachedCryptoApiService = new CachedCryptoApiService(cryptoApiService,coinCache);
    }

    @Test
    @DisplayName("Crypto should be returned from cache")
    void getCryptoFromCache() throws CryptoNotFoundException {
        coinCache.add("BTC",new CryptoCached("BTC",new BigDecimal("100")));
        CryptoCached preparedCrypto = new CryptoCached("BTC", new BigDecimal(100));
        CryptoCached crypto = cachedCryptoApiService.getCrypto("BTC");

        assertEquals(preparedCrypto,crypto);
    }

    @Test
    @DisplayName("Crypto should be returned from API and cached")
    void getCryptoFromApiAndCacheIt() throws CryptoNotFoundException {
        String symbol = "BTC";
        CryptoCached preparedCrypto = new CryptoCached(symbol, new BigDecimal(100));
        when(cryptoApiService.getCrypto(any())).thenReturn(new CryptoCached("BTC", new BigDecimal("100")));
        CryptoCached crypto = cachedCryptoApiService.getCrypto(symbol);

        assertEquals(preparedCrypto,crypto);
        assertEquals(coinCache.get(symbol),preparedCrypto);
    }
}