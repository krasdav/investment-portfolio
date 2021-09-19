package org.dav.equitylookup.service;

import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.cache.StockCached;

import java.io.IOException;
import java.math.BigDecimal;

public interface StockApiService {
    StockCached findStock(String ticker) throws IOException, StockNotFoundException;

    BigDecimal findPrice(StockCached stockCached) throws IOException, StockNotFoundException;

    BigDecimal findPrice(String ticker) throws IOException, StockNotFoundException;

    BigDecimal getPrice(String ticker) throws IOException, StockNotFoundException;
}
