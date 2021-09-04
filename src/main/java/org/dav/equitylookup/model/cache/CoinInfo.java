package org.dav.equitylookup.model.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinInfo {

    private String symbol;
    private String currentPrice;

    public CoinInfo(String symbol, String currentPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }
}
