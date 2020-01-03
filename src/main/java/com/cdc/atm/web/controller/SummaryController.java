package com.cdc.atm.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Provide controller for Summary screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class SummaryController {

    private AccountComponent accountComponent;

    @Autowired
    public SummaryController(AccountComponent accountComponent) {
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/summary")
    public ModelAndView getSummaryPage(@ModelAttribute(value = Summary.Metadata.MODEL) Summary summary) {
        if (accountComponent == null || accountComponent.getAccountNumber() == null
                || "".equals(accountComponent.getAccountNumber().trim())) {
            return new ModelAndView("redirect:/welcome");
        }
        return new ModelAndView("summary");
    }

    @PostMapping(value = "/summary")
    public ModelAndView postSummaryPage(@Valid @ModelAttribute(value = Summary.Metadata.MODEL) Summary summary,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("summary");
        }

        // Decide redirect page based on option (default to Welcome page)
        String redirectView;
        if (Summary.Option.TRANSACTION.toString().equalsIgnoreCase(summary.getOption())) {
            redirectView = "transaction";
        } else {
            redirectView = "welcome";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
