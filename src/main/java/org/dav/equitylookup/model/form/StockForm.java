package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class StockForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;
    @NotEmpty(message = "Amount cannot be empty")
    private int amount;
    @NotEmpty(message = "Price cannot be empty")
    private BigDecimal price;
    @NotEmpty(message = "Date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
