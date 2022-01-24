package org.dav.portfoliotracker.service;

import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.dav.portfoliotracker.model.dto.StockDTO;

import java.io.IOException;
import java.util.List;

public interface StockService {

    StockCached getStock(String ticker) throws IOException, StockNotFoundException;

    List<StockDTO> getAnalyzedStockDTOS(Portfolio portfolio) throws IOException, StockNotFoundException;

    void setDynamicData(List<StockDTO> stockDTOS) throws IOException, StockNotFoundException;

    List<StockDTO> getAndRemoveSoldOutStocks(List<StockDTO> stockDTOS);
}
