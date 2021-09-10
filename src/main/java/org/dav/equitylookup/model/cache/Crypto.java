package org.dav.equitylookup.model.cache;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
public class Crypto {

    private final String symbol;
    private final String currentPrice;
}
