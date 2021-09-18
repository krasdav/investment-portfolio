package org.dav.equitylookup.service;

import org.dav.equitylookup.model.cache.StockCached;

import java.io.IOException;
import java.math.BigDecimal;

public interface StockApiService {
    StockCached findStock(String ticker) throws IOException;

    BigDecimal findPrice(StockCached stockCached) throws IOException;

    BigDecimal findPrice(String ticker) throws IOException;

    BigDecimal getPrice(String ticker) throws IOException;
}
