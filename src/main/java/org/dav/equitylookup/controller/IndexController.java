package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.dto.UserDTO;
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

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/index")
    public String frontPage() {
        return "index";
    }

    @GetMapping("/registration")
    public String saveUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registration-form";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else if (userService.getUserByUsername(userDTO.getUsername()) != null &&
                userService.findByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("username", "user.username", "Username with this email and username already exists");
            return "registration-form";
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setRole("ROLE_USER");
        userService.saveUser(user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "registration-success";
    }
}
