package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;

import java.io.IOException;
import java.math.BigDecimal;

public interface StockApiService {
    Stock findStock(String ticker) throws IOException;
    BigDecimal findPrice(Stock stock) throws IOException;
    BigDecimal findPrice(String ticker) throws IOException;
    BigDecimal getPrice(String ticker) throws IOException;
}
