package org.dav.equitylookup.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockDTO {

    private Long id;
    private String ticker;
    private BigDecimal currentPrice;

}
