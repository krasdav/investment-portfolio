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
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/user")
    public String welcomePage(Model model) {
        return "user/user-welcome";
    }

    @GetMapping("/user/stocks/list")
    public String listStocksForm(Model model) {
        model.addAttribute("user", new User());
        return "user/user-stock-query";
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

}
