package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.model.dto.GroupedStockSharesDTO;
import org.dav.equitylookup.model.dto.StockShareDTO;

import java.io.IOException;
import java.util.List;

public interface StockService {
    List<Stock> updateStockPrices(Portfolio portfolio) throws IOException;

    List<Stock> updateStockPrices(List<StockShare> stockShares) throws IOException;

    StockShare obtainShare(String ticker, User user) throws IOException;

    void cacheStocks(Stock... stocks);

    List<Stock> getTopStocks() throws IOException;

    Stock getStock(String ticker) throws IOException;

    List<GroupedStockSharesDTO> obtainGroupedAnalyzedDTO(Portfolio portfolio) throws IOException;

    void analyze(List<GroupedStockSharesDTO> stockShareDTOS) throws IOException;
}
