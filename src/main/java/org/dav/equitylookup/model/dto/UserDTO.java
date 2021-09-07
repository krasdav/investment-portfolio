package org.dav.equitylookup.model.dto;

import lombok.Data;
import org.dav.equitylookup.model.Portfolio;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Portfolio portfolio;
}
