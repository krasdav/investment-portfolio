package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

import java.time.LocalDateTime;

@Data
public class CryptoShareDTO {

    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    private Portfolio portfolio;
    private String boughtPrice;
    private double amount;
    private String currentPrice;
    private String holdings;
    private String percentageChange;
    private String valueChange;

    public void addAmount(double amount){
        this.amount = this.amount + amount;
    }

    public void addBoughtPrice(String boughtPrice){
        this.boughtPrice = String.valueOf(Double.parseDouble(this.boughtPrice) + Double.parseDouble(boughtPrice));
    }
}
