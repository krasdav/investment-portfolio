package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.TransactionRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StockDTO {

    private Long id;
    private LocalDateTime timeBought;
    private BigDecimal totalCostPrice;
    private BigDecimal totalProceedsPrice;
    private int quantity = 0;
    private String ticker;
    private String company;
    private Portfolio portfolio;
    private List<TransactionRecord> transactionRecords = new ArrayList<>();

    private BigDecimal marketValue;
    private BigDecimal currentPrice;
    private BigDecimal percentageChange;
    private BigDecimal valueChange;


}
