package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShareDTO {

    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal boughtPrice;
    private BigDecimal holdings;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private int count = 1;
    private String ticker;
    private String company;
    private Portfolio portfolio;

    public void addBoughtPrice(BigDecimal boughtPrice){
        this.boughtPrice = this.boughtPrice.add(boughtPrice);
    }

    public void incrementCount(){
        count++;
    }

}
