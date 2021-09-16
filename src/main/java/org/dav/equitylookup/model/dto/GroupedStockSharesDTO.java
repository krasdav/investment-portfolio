package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;

import java.math.BigDecimal;

@Data
public class GroupedStockSharesDTO {

    private BigDecimal totalPurchasePrice;
    private BigDecimal currentPrice;
    private BigDecimal holdings;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private String ticker;
    private String company;
    private Portfolio portfolio;
    private int amount = 1;

    public GroupedStockSharesDTO(StockShare stockShare){
        this.totalPurchasePrice = stockShare.getPurchasePrice();
        this.ticker = stockShare.getTicker();
        this.company = stockShare.getCompany();
        this.portfolio = stockShare.getPortfolio();
    }

    public void addToPurchasePrice(BigDecimal purchasePrice){
        this.totalPurchasePrice = this.totalPurchasePrice.add(purchasePrice);
    }

    public void incrementAmount(){
        this.amount++;
    }
}
