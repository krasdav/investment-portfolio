package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.cache.CryptoCached;

public interface CryptoApiService {
    CryptoCached getCrypto(String symbol) throws CryptoNotFoundException;
}
