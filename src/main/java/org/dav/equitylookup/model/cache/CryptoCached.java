package org.dav.equitylookup.model.cache;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CryptoCached {

    private final String symbol;
    private final BigDecimal currentPrice;

}
