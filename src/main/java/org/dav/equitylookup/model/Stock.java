package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "stocks")
    private List<User> users = new ArrayList<>();

    public Stock() {
    }

    public Stock(String ticker) {
        this.ticker = ticker;
    }

    public Stock(BigDecimal price) {
        this.currentPrice = price;
    }

    public void addUser(User user){
        users.add(user);
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
