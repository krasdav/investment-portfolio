package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockShareDTO {

    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal purchasePrice;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private String ticker;
    private String company;
    private Portfolio portfolio;
}
