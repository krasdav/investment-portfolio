package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PortfolioDTO {

    private String name;
    private BigDecimal portfolioValue;
    private LocalDateTime creationTime;
    private User user;
    private List<StockShare> stockShares;
    private List<CryptoShare> cryptocurrencies;
}
