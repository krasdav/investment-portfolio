package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.form.StockForm;
import org.dav.equitylookup.service.implementation.StockSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class StockController {


    private final StockSearchService stockSearchService;

    @GetMapping("/stock")
    public String stockForm(Model model) {
        model.addAttribute("stockForm",new StockForm());
        return "stock/stock-query";
    }

    @PostMapping("/stock")
    public String stockPrice(@ModelAttribute StockForm stockForm, Model model) throws IOException {
        String stockPrice = stockSearchService.findPrice(stockSearchService.findStock(stockForm.getTicker())).toString();
        model.addAttribute("stock",stockForm.getTicker());
        model.addAttribute("stockPrice", stockPrice);
        return "stock/stock-result";
    }

}
