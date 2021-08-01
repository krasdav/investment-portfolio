package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class UserStockInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal boughtPrice;
    @OneToOne
    private Stock stock;
    @ManyToOne
    private User user;


    public UserStockInfo() {
    }

    public UserStockInfo(LocalDateTime timeBought, BigDecimal boughtPrice, Stock stock, User user) {
        this.timeBought = timeBought;
        this.boughtPrice = boughtPrice;
        this.stock = stock;
        this.user = user;
    }
}
