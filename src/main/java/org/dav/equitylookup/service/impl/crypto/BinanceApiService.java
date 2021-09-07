package org.dav.equitylookup.service.impl.crypto;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.service.CryptoApiService;
import org.springframework.stereotype.Service;

@Service("binanceApiService")
public class BinanceApiService implements CryptoApiService {

    private final BinanceApiRestClient client;

    public BinanceApiService() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        this.client = factory.newRestClient();
    }

    @Override
    public Crypto getCrypto(String symbol) {
        TickerPrice coin = client.getPrice(symbol + "USDT");
        return new Crypto(symbol, coin.getPrice());
    }
}
