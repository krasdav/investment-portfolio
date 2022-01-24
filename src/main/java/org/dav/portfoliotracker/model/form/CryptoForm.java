package org.dav.portfoliotracker.model.form;

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
    @DecimalMin(value = "0.01", message = "Amount must be more then 0")
    private double amount;
    @DecimalMin(value = "0.01", message = "Price must be more then 0")
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
