package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDateTime creationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    public Portfolio() {
    }

    public Portfolio(String name, User user) {
        creationTime = LocalDateTime.now();
        this.name = name;
        this.user = user;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
    }

    public Stock getStockByTicker(String ticker) {
        for (Stock s : stocks) {
            if (s.getTicker().equals(ticker)) {
                return s;
            }
        }
        return null;
    }

    public Cryptocurrency getCryptoCurrencyBySymbol(String symbol) {
        for (Cryptocurrency crypto : cryptocurrencies) {
            if (crypto.getSymbol().equals(symbol)) {
                return crypto;
            }
        }
        return null;
    }

    public void addCryptocurrency(Cryptocurrency cryptocurrency) {
        cryptocurrencies.add(cryptocurrency);
    }

    public void removeCryptocurrency(Cryptocurrency cryptocurrency) {
        cryptocurrencies.remove(cryptocurrency);
    }

}
