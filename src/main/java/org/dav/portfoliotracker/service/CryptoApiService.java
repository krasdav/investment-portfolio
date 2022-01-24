package org.dav.portfoliotracker.service;

import org.dav.portfoliotracker.exceptions.CryptoNotFoundException;
import org.dav.portfoliotracker.model.cache.CryptoCached;

public interface CryptoApiService {
    CryptoCached getCrypto(String symbol) throws CryptoNotFoundException;
}
