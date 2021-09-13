package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
public class CoinForm {

    @NotEmpty(message = "Symbol cannot be empty")
    private String symbol;
    @DecimalMin(value = "0.01", message = "Input invalid")
    private double amount;
    @DecimalMin(value = "0.01", message = "Input invalid")
    private BigDecimal price;
}
