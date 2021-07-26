package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.dto.UserDTO;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final StockService stockService;

    private final ModelMapper modelMapper;


    @GetMapping("/main")
    public String frontPage() {
        return "main";
    }

    @GetMapping("/users/list")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/users")
    public String welcomePage(Model model) {
        return "user-welcome";
    }

    @GetMapping("/users/stocks/list")
    public String listStocksForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-stock-query";
    }

    @PostMapping("/users/stocks/list")
    public String listStocks(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) throws IOException {
        if( bindingResult.hasErrors()){
            return "user-stock-query";
        }

        User user = modelMapper.map(userDTO, User.class);
        user = userService.getUserByNickname(user.getUsername());
        userService.updatePortfolioValue(user);

        userDTO = modelMapper.map(user, UserDTO.class);
        model.addAttribute("stocks", userDTO.getStocks());
        model.addAttribute("portfolioValue", userDTO.getPortfolio());
        return "user-stock-list";
    }

    @GetMapping("/users/add")
    public String saveUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-add-form";
    }

    @PostMapping("/users/add")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if( bindingResult.hasErrors()){
            return "user-add-form";
        }else if( userService.getUserByNickname(userDTO.getUsername()) != null){
            model.addAttribute("registrationError","User in use");
            return "user-add-form";
        }
        User user = modelMapper.map(userDTO, User.class);
        userService.saveUser(user);
        model.addAttribute("userNickname", user.getUsername());
        model.addAttribute("userId", user.getId());
        return "user-result";
    }

    @GetMapping("/users/find")
    public String getUserByNickname(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-find-form";
    }

    @PostMapping("/users/find")
    public String getUserByNickname(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) throws IOException {

        if( bindingResult.hasErrors()){
            return "user-find-form";
        }

        User user = modelMapper.map(userDTO, User.class);
        user = userService.getUserByNickname(user.getUsername());
        userService.updatePortfolioValue(user);

        userDTO = modelMapper.map(user, UserDTO.class);

        model.addAttribute("userNickname", userService.getUserByNickname(userDTO.getUsername()).getUsername());
        model.addAttribute("stocks", userService.getUserByNickname(userDTO.getUsername()).getStocks());
        model.addAttribute("portfolio", userService.getUserByNickname(userDTO.getUsername()).getPortfolio());
        return "user-find-res";
    }

}
