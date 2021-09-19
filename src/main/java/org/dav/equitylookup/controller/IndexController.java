package org.dav.equitylookup.controller;

import com.binance.api.client.exception.BinanceApiException;
import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.form.*;
import org.dav.equitylookup.service.CryptoService;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"stockForm", "cryptoForm"})
public class IndexController {

    private final UserService userService;
    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final PasswordEncoder passwordEncoder;
    private final CryptoService cryptoService;

    @ModelAttribute("stockForm")
    public StockFindForm getStockForm() {
        return new StockFindForm();
    }

    @ModelAttribute("cryptoForm")
    public CryptoFindForm getCryptoForm() {
        return new CryptoFindForm();
    }

    @GetMapping("/index")
    public String frontPage(Model model) {
        model.addAttribute("coin", new CryptoFindForm());
        model.addAttribute("stock", new StockFindForm());
        return "index";
    }

    @PostMapping("/crypto")
    public String getCryptoPrice(@Validated @ModelAttribute("cryptoForm") CryptoFindForm cryptoForm, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttrs){
        if(bindingResult.hasErrors()){
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/index";
        }
        BigDecimal price;
        try {
            price = cryptoService.getCoinPrice(cryptoForm.getSymbol());
        } catch (CryptoNotFoundException | BinanceApiException e) {
            bindingResult.rejectValue("symbol", "error.cryptoForm", "Cryptocurrency not found");
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/index";
        }

        sessionStatus.setComplete();
        redirectAttrs.addFlashAttribute("price", price);
        redirectAttrs.addFlashAttribute("asset", cryptoForm.getSymbol());
        return "redirect:/index";
    }

    @PostMapping("/stock")
    public String getStockPrice(@Validated @ModelAttribute("stockForm") StockFindForm stockFindForm, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttrs) throws IOException{
        if(bindingResult.hasErrors()){
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/index";
        }
        BigDecimal price = new BigDecimal("0");
        try {
            price = stockService.getStock(stockFindForm.getTicker()).getCurrentPrice();
        } catch (StockNotFoundException e) {
            bindingResult.rejectValue("ticker", "error.stockForm", "Stock not found");
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/index";
        }
        sessionStatus.setComplete();
        redirectAttrs.addFlashAttribute("price", price );
        redirectAttrs.addFlashAttribute("asset", stockFindForm.getTicker());
        return "redirect:/index";
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
        user.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
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

    @GetMapping("/user")
    public String welcomePage(Model model, Principal user) {
        model.addAttribute("username", user.getName());
        return "user-welcome";
    }

    @GetMapping("/rest")
    public Portfolio getMap() throws PortfolioNotFoundException {
        return portfolioService.getPortfolioByName("Michal's Portfolio");
    }
}
