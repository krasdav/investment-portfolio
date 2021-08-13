package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal boughtPrice;
    private String ticker;
    @ManyToOne
    private Stock stock;
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    public Share() {
    }

    public Share(BigDecimal boughtPrice, Stock stock, User user) {
        this.ticker = stock.getTicker();
        this.timeBought = LocalDateTime.now();
        this.boughtPrice = boughtPrice;
        this.stock = stock;
        this.portfolio = user.getPortfolio();
    }
}
