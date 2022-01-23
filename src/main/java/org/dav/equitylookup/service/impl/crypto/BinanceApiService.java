package org.dav.equitylookup.service.impl.crypto;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.exception.BinanceApiException;
import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.dav.equitylookup.service.CryptoApiService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("binanceApiService")
public class BinanceApiService implements CryptoApiService {

    private final BinanceApiRestClient client;

    public BinanceApiService() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        this.client = factory.newRestClient();
    }

    public BinanceApiService(BinanceApiRestClient client) {
        this.client = client;
    }

    @Override
    public CryptoCached getCrypto(String symbol) throws CryptoNotFoundException {
        TickerPrice coin;
        try {
            coin = client.getPrice(symbol + "USDT");
        } catch (BinanceApiException e) {
            throw new CryptoNotFoundException("Cryptocurrency not found or binance api is off");
        }
        return new CryptoCached(symbol, new BigDecimal(coin.getPrice()));
    }
}
