package org.dav.portfoliotracker.model.dto;

import lombok.Data;
import org.dav.portfoliotracker.model.Cryptocurrency;
import org.dav.portfoliotracker.model.User;
import org.dav.portfoliotracker.model.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PortfolioDTO {

    private String name;
    private BigDecimal portfolioValue;
    private LocalDateTime creationTime;
    private User user;
    private List<Cryptocurrency> cryptocurrencies;
    private List<Stock> stocks;
}
