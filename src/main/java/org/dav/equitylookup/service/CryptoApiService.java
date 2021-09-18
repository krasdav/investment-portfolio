package org.dav.equitylookup.service;

import org.dav.equitylookup.model.cache.CryptoCached;

public interface CryptoApiService {
    CryptoCached getCrypto(String symbol);
}
