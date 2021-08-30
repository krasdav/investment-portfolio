package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShareDTO {

    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal boughtPrice;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private String ticker;
    private String company;
    private Portfolio portfolio;

}
