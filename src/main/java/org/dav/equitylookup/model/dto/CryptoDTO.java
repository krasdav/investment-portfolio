package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.TransactionRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CryptoDTO {

    private Long id;
    private LocalDateTime timeBought;
    private String symbol;
    private Portfolio portfolio;
    private BigDecimal totalCostPrice = new BigDecimal("0");
    private BigDecimal totalProceedsPrice = new BigDecimal("0");
    private double fraction;
    private List<TransactionRecord> transactionRecords = new ArrayList<>();

    private BigDecimal marketValue = new BigDecimal("0");
    private BigDecimal percentageChange;
    private BigDecimal valueChange;
    private BigDecimal currentPrice;
}
