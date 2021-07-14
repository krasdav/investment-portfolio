package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Stock() {
    }

    public Stock(String ticker, LocalDateTime dateBought, User user) {
        this.ticker = ticker;
        this.dateBought = dateBought;
        this.user = user;
    }

}
