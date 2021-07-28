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


    @GetMapping("/user/add")
    public String saveUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/user-add-form";
    }

    @PostMapping("/user/add")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/user-add-form";
        } else if (userService.getUserByUsername(userDTO.getUsername()) != null) {
            model.addAttribute("registrationError", "User in use");
            return "user/user-add-form";
        }
        User user = modelMapper.map(userDTO, User.class);
        userService.saveUser(user);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("userId", user.getId());
        return "user/user-result";
    }

    @PostMapping("/user/stocks/list")
    public String listStocks(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "user/user-stock-query";
        }

        User user = modelMapper.map(userDTO, User.class);
        user = userService.getUserByUsername(user.getUsername());
        userService.updatePortfolioValue(user);

        userDTO = modelMapper.map(user, UserDTO.class);
        model.addAttribute("stocks", userDTO.getStocks());
        model.addAttribute("portfolioValue", userDTO.getPortfolio());
        return "user/user-stock-list";
    }
}
