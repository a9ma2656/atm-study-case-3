package com.cdc.atm.web.controller;

import com.cdc.atm.web.model.Account;
import com.cdc.atm.web.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {

    private WelcomeService service;

    @Autowired
    public WelcomeController(WelcomeService service) {
        this.service = service;
    }

    @GetMapping(value = "/welcome")
    public String getWelcomePage(@ModelAttribute(value = Account.Metadata.MODEL) Account account) {
        return "welcome";
    }

    @PostMapping(value = "/welcome")
    public String postWelcomePage(@Valid @ModelAttribute(Account.Metadata.MODEL) Account account, BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return "welcome";
        }

        boolean isValidAccount = service.validateAccount(account.getAccountNumber(), account.getPin());
        if (!isValidAccount) {
            errors.add("Invalid Account Number/PIN");
            model.put("errors", errors);
            return "welcome";
        }

        return "withdraw";
    }
}
