package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.StockForm;
import org.dav.equitylookup.service.StockSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class StockSerachController {

    private final StockSearchService stockSearchService;

    @GetMapping("/stock")
    public String stockForm(Model model) {
        model.addAttribute("stockForm",new StockForm());
        return "stock-query";
    }

    @PostMapping("/stock")
    public String stockPrice(@ModelAttribute StockForm stockForm, Model model) throws IOException {
        String stockPrice = stockSearchService.findPrice(stockSearchService.findStock(stockForm.getTicker())).toString();
        model.addAttribute("stock",stockForm.getTicker());
        model.addAttribute("stockPrice", stockPrice);
        return "stock-result";
    }

}
