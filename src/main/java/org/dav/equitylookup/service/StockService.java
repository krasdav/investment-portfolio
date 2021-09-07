package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Stock;
import org.dav.equitylookup.model.dto.ShareDTO;

import java.io.IOException;
import java.util.List;

public interface StockService {
    List<Stock> updateStockPrices(Portfolio portfolio) throws IOException;

    List<Stock> updateStockPrices(List<Share> shares) throws IOException;

    void addAnalysisDetails(List<ShareDTO> shareDTOS) throws IOException;

    Share obtainShare(String ticker, User user) throws IOException;

    void cacheStocks(Stock... stocks);

    List<Stock> getTopStocks() throws IOException;

    Stock getStock(String ticker) throws IOException;
}
