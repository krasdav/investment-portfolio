package org.dav.equitylookup.controller;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @GetMapping("/users/list")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
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
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId", user.getId());
        return "user-result";
    }

    @GetMapping("/stocks/add")
    public String addStock(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("user", new User());
        return "user-stock-add";
    }

    @PostMapping("/stocks/add")
    public String addStock(@ModelAttribute("user") User user, @ModelAttribute("stock") Stock stock, Model model) {
        User newUser = userService.getUserByNickname(user.getNickname());
        newUser.addStock(stock);
        stock.setUser(newUser);
        stockService.saveStock(stock);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("ticker", stock.getTicker());
        return "user-stock-result";
    }

    @GetMapping("/users/{nickname}")
    public String getUserByNickname(@PathVariable("nickname") String nickname, Model model) {
        User user = userService.getUserByNickname(nickname);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId", user.getId());
        return "user-details";
    }

}
