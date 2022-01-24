package org.dav.portfoliotracker.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CryptoFindForm {

    @NotEmpty(message = "Symbol cant be empty")
    private String symbol;
}
