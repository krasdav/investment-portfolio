package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.enums.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRecordDTO {

    private long id;
    private LocalDate timeBought;
    private String assetSymbol;
    private String asset;
    private BigDecimal value;
    private Operation operation;
    private double quantity;
}
