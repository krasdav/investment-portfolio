package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Cryptocurrency;
import org.dav.equitylookup.model.Stock;
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
    private List<Cryptocurrency> cryptocurrencies;
    private List<Stock> stocks;
}
