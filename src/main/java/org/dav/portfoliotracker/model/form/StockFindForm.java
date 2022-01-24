package org.dav.portfoliotracker.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StockFindForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;
}
