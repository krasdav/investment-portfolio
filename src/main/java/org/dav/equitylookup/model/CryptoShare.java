package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.cache.Crypto;

import javax.persistence.*;
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
    private String boughtPrice;
    private double amount;

    public CryptoShare() {
    }

    public CryptoShare(double amount, Crypto crypto, User user) {
        this.timeBought = LocalDateTime.now();
        this.symbol = crypto.getSymbol();
        this.portfolio = user.getPortfolio();
        this.boughtPrice = String.valueOf(Double.parseDouble(crypto.getCurrentPrice()) * amount);
        this.amount = amount;
    }
}
