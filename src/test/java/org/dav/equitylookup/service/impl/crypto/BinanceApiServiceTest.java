package org.dav.equitylookup.service.impl.crypto;

import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.model.cache.CryptoCached;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BinanceApiServiceTest {

    private final BinanceApiService binanceApiService = new BinanceApiService();

    @Test
    @DisplayName("Binance API should return cryptocurrency")
    void getCrypto() throws CryptoNotFoundException {
        String symbol = "BTC";
        CryptoCached preparedCrypto = new CryptoCached("BTC", new BigDecimal("100"));

        CryptoCached crypto = binanceApiService.getCrypto(symbol);
        assertEquals(crypto.getSymbol(), preparedCrypto.getSymbol());
    }

    @Test
    @DisplayName("Binance API should throw exceptions")
    void getCryptoException() {
        String invalidSymbol = "ASD";
        String illegalChars = "asdasd";

        CryptoNotFoundException thrown = assertThrows(
                CryptoNotFoundException.class, () -> binanceApiService.getCrypto(invalidSymbol),
                "Expected CryptoNotFoundException, but it didnt"
        );
        assertTrue(thrown.getMessage().contains("Cryptocurrency not found"));

        thrown = assertThrows(
                CryptoNotFoundException.class, () -> binanceApiService.getCrypto(illegalChars),
                "Expected CryptoNotFoundException, but it didnt"
        );
        assertTrue(thrown.getMessage().contains("Cryptocurrency not found"));
    }
}