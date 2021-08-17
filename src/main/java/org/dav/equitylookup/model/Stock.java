package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ticker;
    private BigDecimal currentPrice;

    public Stock() {
    }

    public Stock(String ticker, BigDecimal currentPrice) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
    }

    public Stock(String ticker) {
        this.ticker = ticker;
    }

    public Stock(BigDecimal price) {
        this.currentPrice = price;
    }

    @Override
    public String toString() {
        return "Stock : {" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
