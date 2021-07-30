package org.dav.equitylookup.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    private BigDecimal portfolio = new BigDecimal("0");
    private List<Stock> stocks = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.portfolio = BigDecimal.valueOf(user.getPortfolio().doubleValue());
        this.stocks.addAll(user.getStocks());
    }
}
