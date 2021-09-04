package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.cache.CoinInfo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;
    private String boughtPrice;

    public Coin() {
    }

    public Coin(String boughtPrice, CoinInfo coinInfo, User user) {
        this.timeBought = LocalDateTime.now();
        this.symbol = coinInfo.getSymbol();
        this.portfolio = user.getPortfolio();
        this.boughtPrice = boughtPrice;
    }
}
