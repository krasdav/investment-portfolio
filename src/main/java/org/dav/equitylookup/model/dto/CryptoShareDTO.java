package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CryptoShareDTO {

    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    private Portfolio portfolio;
    private BigDecimal holdings;
    private BigDecimal purchasePrice;
    private BigDecimal pricePerShare;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private double fraction;
}
