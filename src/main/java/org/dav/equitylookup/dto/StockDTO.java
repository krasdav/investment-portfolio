package org.dav.equitylookup.dto;

import lombok.Data;
import org.dav.equitylookup.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockDTO {

    private Long id;
    private String ticker;
    private LocalDateTime dateBought;
    private BigDecimal price;
    private BigDecimal boughtPrice;
    private User user;

}
