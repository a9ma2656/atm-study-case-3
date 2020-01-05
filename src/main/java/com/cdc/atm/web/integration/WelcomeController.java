package com.cdc.atm.web.integration;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.Welcome;
import com.cdc.atm.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide controller for Welcome screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class WelcomeController {

    private AccountService   service;
    private AccountComponent accountComponent;

    @Autowired
    public WelcomeController(AccountService service, AccountComponent accountComponent) {
        this.service = service;
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/")
    public ModelAndView getIndex(@ModelAttribute(value = Welcome.Metadata.MODEL) Welcome welcome) {
        return new ModelAndView("redirect:/welcome");
    }

    @GetMapping(value = "/welcome")
    public ModelAndView getWelcomePage(@ModelAttribute(value = Welcome.Metadata.MODEL) Welcome welcome) {
        accountComponent.setAccountNumber(null);
        return new ModelAndView("welcome");
    }

    @PostMapping(value = "/welcome")
    public ModelAndView postWelcomePage(@Valid @ModelAttribute(Welcome.Metadata.MODEL) Welcome welcome,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("welcome");
        }

        boolean isValidAccount = service.validateAccount(welcome.getAccountNumber(), welcome.getPin());
        if (!isValidAccount) {
            errors.add("Invalid Account Number/PIN");
            model.put("errors", errors);
            return new ModelAndView("welcome");
        }

        accountComponent.setAccountNumber(welcome.getAccountNumber());
        return new ModelAndView("redirect:/transaction");
    }
}
