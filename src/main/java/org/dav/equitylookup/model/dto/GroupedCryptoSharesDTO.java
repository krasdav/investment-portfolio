package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;

import java.math.BigDecimal;

@Data
public class GroupedCryptoSharesDTO {

    private String symbol;
    private Portfolio portfolio;
    private BigDecimal totalPurchasePrice;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private BigDecimal holdings;
    private double amount;

    public GroupedCryptoSharesDTO(CryptoShare cryptoShare) {
        this.symbol = cryptoShare.getSymbol();
        this.portfolio = cryptoShare.getPortfolio();
        this.totalPurchasePrice = new BigDecimal(cryptoShare.getBoughtPrice());
        this.amount = cryptoShare.getFraction();
    }

    public void addToPurchasePrice(BigDecimal purchasePrice) {
        this.totalPurchasePrice = this.totalPurchasePrice.add(purchasePrice);
    }

    public void addToAmount(double amount) {
        this.amount = this.amount + amount;
    }
}
