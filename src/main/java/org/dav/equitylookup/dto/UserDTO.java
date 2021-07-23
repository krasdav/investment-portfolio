package org.dav.equitylookup.dto;

import lombok.Data;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String nickname;
    private BigDecimal portfolio;
    private List<Stock> stocks = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.portfolio = BigDecimal.valueOf(user.getPortfolio().doubleValue());
        this.stocks.addAll(user.getStocks());
    }

}
