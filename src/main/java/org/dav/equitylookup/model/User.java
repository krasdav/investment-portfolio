package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name  = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nickname;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> stocks;

    public User(){};

    public User(String nickname){
        this.nickname = nickname;
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public void removeStock(Stock stock){
        stocks.remove(stock);
    }

}
