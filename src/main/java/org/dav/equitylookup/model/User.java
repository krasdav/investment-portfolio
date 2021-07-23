package org.dav.equitylookup.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name  = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nickname;
    private BigDecimal portfolio = new BigDecimal("0");

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> stocks = new ArrayList<>();

    public User(){}

    public User(String nickname){
        this.nickname = nickname;
    }

    public User(Long id, String nickname, BigDecimal portfolio) {
        this.id = id;
        this.nickname = nickname;
        this.portfolio = portfolio;
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public void addStocks(List<Stock> stocks){
        this.stocks.addAll(stocks);
    }

    public void removeStock(Stock stock){
        stocks.remove(stock);
    }

    public void addToPortfolio(BigDecimal stockValue){
        portfolio = portfolio.add(stockValue);
    }

}
