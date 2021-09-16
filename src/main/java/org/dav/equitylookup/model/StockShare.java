package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.cache.Stock;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StockShare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timeBought;
    private String ticker;
    private String company;
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;
    private BigDecimal purchasePrice;

    public StockShare() {
    }

    public StockShare(BigDecimal boughtPrice, Stock stock, User user) {
        this.ticker = stock.getTicker();
        this.company = stock.getCompany();
        this.timeBought = LocalDateTime.now();
        this.purchasePrice = boughtPrice;
        this.portfolio = user.getPortfolio();
    }
}
