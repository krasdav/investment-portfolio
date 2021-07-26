package org.dav.equitylookup.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StockForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;
}
