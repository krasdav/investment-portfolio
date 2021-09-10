package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CoinForm {

    @NotEmpty(message = "Symbol cannot be empty")
    private String symbol;
    @DecimalMin(value = "0.01", message = "Your message...")
    private double amount;
}
