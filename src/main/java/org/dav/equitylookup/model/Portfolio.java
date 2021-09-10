package org.dav.equitylookup.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
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
    private List<StockShare> stockShares = new ArrayList<>();

    @OneToMany(
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CryptoShare> cryptoShares = new ArrayList<>();

    public Portfolio() {
    }

    public Portfolio(String name, User user) {
        creationTime = LocalDateTime.now();
        this.name = name;
        this.user = user;
    }

    public List<StockShare> getStockShares() {
        return stockShares;
    }

    public List<String> getStockTickers() {
        return stockShares.stream().map(StockShare::getTicker).collect(Collectors.toList());
    }

    public void removeShare(StockShare stockShare) {
        stockShares.remove(stockShare);
    }

    public void addShare(StockShare stockShare)    {
        stockShares.add(stockShare);
    }

    public List<StockShare> getStockSharesByCompany(String ticker){
        return stockShares.stream().filter(s -> s.getTicker().equals(ticker)).collect(Collectors.toList());
    }

    public List<CryptoShare> getCryptoSharesBySymbol(String symbol){
        return cryptoShares.stream().filter( c -> c.getSymbol().equals(symbol)).collect(Collectors.toList());
    }

    public List<CryptoShare> getCryptocurrencies() {
        return cryptoShares;
    }

    public void removeCoin(CryptoShare cryptoShare) {
        cryptoShares.remove(cryptoShare);
    }

    public void addCoin(CryptoShare cryptoShare) {
        cryptoShares.add(cryptoShare);
    }

}
