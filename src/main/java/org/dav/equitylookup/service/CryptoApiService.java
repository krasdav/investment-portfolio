package org.dav.equitylookup.service;

import org.dav.equitylookup.model.cache.Crypto;

public interface CryptoApiService {
    Crypto getCrypto(String symbol);
}
