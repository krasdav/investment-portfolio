package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
public class ShareForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;
    @NotEmpty(message = "Amount cannot be empty")
    private int amount;
    @NotEmpty(message = "Price cannot be empty")
    private BigDecimal price;
}
