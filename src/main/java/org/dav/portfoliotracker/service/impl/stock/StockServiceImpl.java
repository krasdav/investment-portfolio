package org.dav.portfoliotracker.service.impl.stock;

import lombok.RequiredArgsConstructor;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.service.StockApiService;
import org.dav.portfoliotracker.exceptions.StockNotFoundException;
import org.dav.portfoliotracker.helper.FinancialAnalysis;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.dav.portfoliotracker.model.dto.StockDTO;
import org.dav.portfoliotracker.service.StockService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockApiService cachedStockApiService;

    private final ModelMapper modelMapper;

    @Override
    public StockCached getStock(String ticker) throws IOException, StockNotFoundException {
        return cachedStockApiService.findStock(ticker);
    }

    @Override
    public List<StockDTO> getAnalyzedStockDTOS(Portfolio portfolio) throws IOException, StockNotFoundException {
        List<StockDTO> stockDTOS = modelMapper.map(portfolio.getStocks(), new TypeToken<List<StockDTO>>() {
        }.getType());
        setDynamicData(stockDTOS);
        return stockDTOS;
    }

    @Override
    public void setDynamicData(List<StockDTO> stockDTOS) throws IOException, StockNotFoundException {
        for (StockDTO stockDTO : stockDTOS) {
            BigDecimal currentPrice = cachedStockApiService.findPrice(stockDTO.getTicker());
            BigDecimal holdingsMarketValue = currentPrice.multiply(new BigDecimal(stockDTO.getQuantity()));
            stockDTO.setCurrentPrice(currentPrice);
            stockDTO.setMarketValue(holdingsMarketValue);
            stockDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(
                    stockDTO.getTotalCostPrice(),
                    stockDTO.getTotalProceedsPrice(),
                    holdingsMarketValue,
                    currentPrice));
            stockDTO.setValueChange(FinancialAnalysis.getValueChange(
                    stockDTO.getTotalCostPrice(),
                    stockDTO.getTotalProceedsPrice(),
                    holdingsMarketValue));
        }
    }

    @Override
    public List<StockDTO> getAndRemoveSoldOutStocks(List<StockDTO> stockDTOS) {
        List<StockDTO> soldOutStocks = new ArrayList<>();
        for (StockDTO stockDTO : stockDTOS) {
            if (stockDTO.getQuantity() == 0) {
                soldOutStocks.add(stockDTO);
            }
        }
        stockDTOS.removeIf(s -> s.getQuantity() == 0);
        return soldOutStocks;
    }
}
