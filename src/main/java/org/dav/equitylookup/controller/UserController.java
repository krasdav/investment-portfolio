package org.dav.equitylookup.controller;

import org.dav.equitylookup.model.User;
import org.dav.equitylookup.repository.UserRepository;
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

    @GetMapping("/users/list")
    public String viewUsers(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/add")
    public String saveEmployee(Model model){
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/users/add")
    public String saveEmployee(@ModelAttribute("user") User user, Model model ){
        userService.saveUser(user);
        model.addAttribute("userNickname",user.getNickname());
        model.addAttribute("userId",user.getId());
        return "user-result";
    }

    @GetMapping("/users/{nickname}")
    public String getUserByNickname(@PathVariable("nickname") String nickname, Model model){
        User user = userService.getUserByNickname(nickname);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId",user.getId());
        return "user-details";
    }

}
