package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.dto.UserDTO;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final StockService stockService;

    private final ModelMapper modelMapper;

    @GetMapping("/users/list")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        System.out.println(stockService.findFirstUser());
        return "user-list";
    }

    @PostMapping("/users/stocks/list")
    public String listStocks(@ModelAttribute UserDTO userDto, Model model) throws IOException {
        User user = modelMapper.map(userDto, User.class);

        user = userService.getUserByNickname(user.getNickname());
        userService.updatePortfolioValue(user);

        userDto = modelMapper.map(user, UserDTO.class);
        model.addAttribute("stocks", userDto.getStocks());
        model.addAttribute("portfolioValue", userDto.getPortfolio());
        return "user-stock-list";
    }

    @GetMapping("/users/stocks/list")
    public String listStocksForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-stock-query";
    }

    @GetMapping("/users/add")
    public String saveUser(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @GetMapping("/main")
    public String frontPage() {
        return "main";
    }

    @PostMapping("/users/add")
    public String saveUser(@ModelAttribute("user") UserDTO userDto, Model model) {
        User user = modelMapper.map(userDto, User.class);
        userService.saveUser(user);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId", user.getId());
        return "user-result";
    }

    @GetMapping("/users/{nickname}")
    public String getUserByNickname(@PathVariable("nickname") String nickname, Model model) {
        UserDTO userDto = modelMapper.map(userService.getUserByNickname(nickname), UserDTO.class);
        model.addAttribute("userNickname", userDto.getNickname());
        model.addAttribute("userId", userDto.getId());
        return "user-details";
    }

}
