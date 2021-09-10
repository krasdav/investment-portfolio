package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.cache.Crypto;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal boughtPrice;
    private double fraction;


    public CryptoShare() {
    }

    public CryptoShare(double amount, Crypto crypto, User user) {
        this.timeBought = LocalDateTime.now();
        this.symbol = crypto.getSymbol();
        this.portfolio = user.getPortfolio();
        this.boughtPrice = crypto.getCurrentPrice().multiply(BigDecimal.valueOf(amount));
        this.fraction = amount;
    }
}
