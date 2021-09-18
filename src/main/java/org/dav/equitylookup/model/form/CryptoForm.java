package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CryptoForm {

    @NotEmpty(message = "Symbol cannot be empty")
    private String symbol;
    @DecimalMin(value = "0.01", message = "Input invalid")
    private double amount;
    @DecimalMin(value = "0.01", message = "Input invalid")
    private BigDecimal price;
    @NotEmpty(message = "Symbol cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
