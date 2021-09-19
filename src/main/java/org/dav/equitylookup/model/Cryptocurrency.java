package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.enums.Operation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRYPTO_ID")
    private Long id;
    private String symbol;
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;
    private double fraction = 0.0;
    private BigDecimal totalCostPrice = new BigDecimal("0");
    private BigDecimal totalProceedsPrice = new BigDecimal("0");

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @MapsId("id")
    private List<TransactionRecord> transactionRecords = new ArrayList<>();

    public Cryptocurrency() {
    }

    public Cryptocurrency(String symbol, Portfolio portfolio) {
        this.symbol = symbol;
        this.portfolio = portfolio;
    }

    public void addTransaction(TransactionRecord transactionRecord) {
        transactionRecords.add(transactionRecord);
        if (transactionRecord.getOperation() == Operation.BUY) {
            fraction = fraction + transactionRecord.getQuantity();
            totalCostPrice = totalCostPrice.add(transactionRecord.getValue());
        } else if (transactionRecord.getOperation() == Operation.SELL) {
            fraction = fraction - transactionRecord.getQuantity();
            if (fraction < 0.0) {
                fraction = 0.0;
            }
            totalProceedsPrice = totalProceedsPrice.add(transactionRecord.getValue());
        }
    }
}
