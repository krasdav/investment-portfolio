package org.dav.portfoliotracker.model.dto;

import lombok.Data;
import org.dav.portfoliotracker.model.Portfolio;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Portfolio portfolio;
}
