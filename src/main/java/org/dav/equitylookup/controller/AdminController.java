package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.dto.UserDTO;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/users/list")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/users/find")
    public String getUserByUsername(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-find-form";
    }

    @PostMapping("/users/find")
    public String getUserByUsername(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "user-find-form";
        }

        User user = modelMapper.map(userDTO, User.class);
        user = userService.getUserByUsername(user.getUsername());
        userService.updatePortfolioValue(user);

        userDTO = modelMapper.map(user, UserDTO.class);

        model.addAttribute("username", userService.getUserByUsername(userDTO.getUsername()).getUsername());
        model.addAttribute("stocks", userService.getUserByUsername(userDTO.getUsername()).getStocks());
        model.addAttribute("portfolio", userService.getUserByUsername(userDTO.getUsername()).getPortfolio());
        return "user-find-res";
    }
}
