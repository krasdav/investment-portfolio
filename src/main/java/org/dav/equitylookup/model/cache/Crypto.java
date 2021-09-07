package org.dav.equitylookup.model.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crypto {

    private String symbol;
    private String currentPrice;

    public Crypto(String symbol, String currentPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }
}
