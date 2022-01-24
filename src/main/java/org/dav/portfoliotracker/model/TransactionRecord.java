package org.dav.portfoliotracker.model;

import lombok.Getter;
import lombok.Setter;
import org.dav.portfoliotracker.model.enums.Operation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate timeBought;
    private String assetSymbol;
    private String asset;
    private BigDecimal value;
    private Operation operation;
    private double quantity;

    public TransactionRecord() {
    }

    public TransactionRecord(LocalDate timeBought, String assetSymbol, BigDecimal value, Operation operation, double quantity) {
        this.timeBought = timeBought;
        this.assetSymbol = assetSymbol;
        this.value = value;
        this.operation = operation;
        this.quantity = quantity;
    }

    public static TransactionRecordBuilder builder() {
        return new TransactionRecordBuilder();
    }

    public static class TransactionRecordBuilder {
        private LocalDate timeBought;
        private String assetSymbol;
        private BigDecimal value;
        private Operation operation;
        private double quantity;

        public TransactionRecordBuilder timeOfPurchase(final LocalDate date) {
            this.timeBought = date;
            return this;
        }

        public TransactionRecordBuilder asset(final String assetSymbol) {
            this.assetSymbol = assetSymbol;
            return this;
        }

        public TransactionRecordBuilder value(final BigDecimal value) {
            this.value = value;
            return this;
        }

        public TransactionRecordBuilder operation(final Operation operation) {
            this.operation = operation;
            return this;
        }

        public TransactionRecordBuilder quantity(final double quantity) {
            this.quantity = quantity;
            return this;
        }

        public TransactionRecord build() {
            return new TransactionRecord(timeBought, assetSymbol, value, operation, quantity);
        }
    }
}
