package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class StockForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;
    @Min(value = 1, message = "Amount must be more then 0")
    private int amount;
    @Min(value = 1, message = "Price must be more then 0")
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
