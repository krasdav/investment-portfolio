package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.cache.StockCached;
import org.dav.equitylookup.model.dto.StockDTO;

import java.io.IOException;
import java.util.List;

public interface StockService {

    StockCached getStock(String ticker) throws IOException;

    List<StockDTO> getAnalyzedStockDTOS(Portfolio portfolio) throws IOException;

    void setDynamicData(List<StockDTO> stockDTOS) throws IOException;

    List<StockDTO> getAndRemoveSoldOutStocks(List<StockDTO> stockDTOS);
}
