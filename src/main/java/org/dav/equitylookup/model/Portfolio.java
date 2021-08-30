package org.dav.equitylookup.model;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private BigDecimal portfolioValue = new BigDecimal("0");
    private LocalDateTime creationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Share> shares = new ArrayList<>();

    public Portfolio() {
    }

    public Portfolio(String name, User user) {
        creationTime = LocalDateTime.now();
        this.name = name;
        this.user = user;
    }

    public List<Share> getShares() {
        return shares;
    }

    public List<String> getStockTickers() {
        return shares.stream().map(Share::getTicker).collect(Collectors.toList());
    }

    public void setPortfolioValue(BigDecimal portfolioValue){
        this.portfolioValue = portfolioValue;
    }

    public void addToPortfolioValue(BigDecimal shareValue){
        portfolioValue = portfolioValue.add(shareValue);
    }

    public void removeShare(Share share){
        shares.remove(share);
    }

    public void addShare(Share share){
        shares.add(share);
    }

}
