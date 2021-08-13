package org.dav.equitylookup.model.dto;

import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShareDTO {

    private LocalDateTime timeBought;
    private BigDecimal boughtPrice;
    private String ticker;
    private Stock stock;
    private Portfolio portfolio;

}
