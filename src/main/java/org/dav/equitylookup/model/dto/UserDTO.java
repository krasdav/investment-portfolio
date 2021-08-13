package org.dav.equitylookup.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    private Portfolio portfolio = new Portfolio();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.portfolio = user.getPortfolio();
    }
}
