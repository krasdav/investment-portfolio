package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.dto.UserDTO;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.form.UserRegistrationForm;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final PortfolioService portfolioService;

    @GetMapping("/index")
    public String frontPage() {
        return "index";
    }

    @GetMapping("/registration")
    public String saveUser(Model model) {
        model.addAttribute("user", new UserRegistrationForm());
        return "registration-form";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid @ModelAttribute("user") UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else if (userService.getUserByUsername(userRegistrationForm.getUsername()) != null &&
                userService.findByEmail(userRegistrationForm.getEmail()) != null) {
            bindingResult.rejectValue("username", "user.username", "User with this email and username already exists");
            return "registration-form";
        }
        User user = new User(userRegistrationForm.getUsername());
        user.setPassword(userRegistrationForm.getPassword());
        user.setRole("ROLE_USER");
        user.setEmail(userRegistrationForm.getEmail());
        userService.saveUser(user);
        Portfolio portfolio = new Portfolio(userRegistrationForm.getPortfolioName(), user);
        portfolioService.savePortfolio(portfolio);
        user.setPortfolio(portfolio);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "registration-success";
    }
}
