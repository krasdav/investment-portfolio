package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

import java.time.LocalDateTime;

@Data
public class CoinDTO {

    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    private Portfolio portfolio;
    private String boughtPrice;
    private String currentPrice;
    private String percentageChange;
    private String valueChange;
}
