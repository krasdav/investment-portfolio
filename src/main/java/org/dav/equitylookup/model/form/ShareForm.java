package org.dav.equitylookup.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ShareForm {

    @NotEmpty(message = "Ticker cant be empty")
    private String ticker;

}
