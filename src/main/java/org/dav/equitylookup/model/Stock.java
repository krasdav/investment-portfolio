package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ticker;
    private LocalDateTime dateBought;
    private BigDecimal currentPrice;
    private BigDecimal boughtPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Stock() {
    }

    public Stock(String ticker){
        this.ticker = ticker;
        this.dateBought = LocalDateTime.now();
    }

    public Stock(String ticker, User user) {
        this.ticker = ticker;
        this.dateBought = LocalDateTime.now();
        this.user = user;
    }

    public Stock(BigDecimal price) {
        this.currentPrice = price;
        this.dateBought = LocalDateTime.now();
    }

    public void setUser(User user){
        this.dateBought = LocalDateTime.now();
        this.user = user;
    }

    @Override
    public String toString() {
        return "Stock : {" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", dateBought=" + dateBought +
                ", currentPrice=" + currentPrice +
                ", boughtPrice=" + boughtPrice +
                '}';
    }
}
