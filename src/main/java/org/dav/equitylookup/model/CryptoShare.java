package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.cache.Crypto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CryptoShare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;
    private BigDecimal purchasePrice;
    private BigDecimal pricePerShare;
    private double fraction;


    public CryptoShare() {
    }

    public CryptoShare(double amount, BigDecimal boughtPrice, Crypto crypto, User user) {
        this.timeBought = LocalDateTime.now();
        this.symbol = crypto.getSymbol();
        this.portfolio = user.getPortfolio();
        this.purchasePrice = boughtPrice;
        this.pricePerShare = boughtPrice.divide(BigDecimal.valueOf(amount),2, RoundingMode.HALF_EVEN);
        this.fraction = amount;
    }
}
