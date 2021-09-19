package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.enums.Operation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STOCK_ID")
    private Long id;
    private String ticker;
    private String company;
    private BigDecimal totalCostPrice = new BigDecimal("0");
    private BigDecimal totalProceedsPrice = new BigDecimal("0");

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Portfolio portfolio;
    private int quantity = 0;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @MapsId("id")
    private List<TransactionRecord> transactionRecords = new ArrayList<>();

    public Stock() {
    }

    public Stock(String ticker, String company, Portfolio portfolio) {
        this.ticker = ticker;
        this.company = company;
        this.portfolio = portfolio;
    }

    public void addTransaction(TransactionRecord transactionRecord) {
        transactionRecords.add(transactionRecord);
        if (transactionRecord.getOperation() == Operation.BUY) {
            quantity = quantity + (int) transactionRecord.getQuantity();
            totalCostPrice = totalCostPrice.add(transactionRecord.getValue());
        } else if (transactionRecord.getOperation() == Operation.SELL) {
            totalProceedsPrice = totalProceedsPrice.add(transactionRecord.getValue());
            quantity = quantity - (int) transactionRecord.getQuantity();
            if (quantity < 0) {
                quantity = 0;
            }
        }
    }
}
