package com.cdc.atm.web.integration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.FundTransferSummary;
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
 * Provide controller for Fund Transfer Summary screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class FundTransferSummaryController {

    private AccountComponent accountComponent;

    @Autowired
    public FundTransferSummaryController(AccountComponent accountComponent) {
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/fundTransferSummary")
    public ModelAndView getSummaryPage(
            @ModelAttribute(value = FundTransferSummary.Metadata.MODEL) FundTransferSummary fundTransferSummary) {
        if (accountComponent == null || accountComponent.getAccountNumber() == null
                || "".equals(accountComponent.getAccountNumber().trim())) {
            return new ModelAndView("redirect:/welcome");
        }
        return new ModelAndView("fundTransferSummary");
    }

    @PostMapping(value = "/fundTransferSummary")
    public ModelAndView postSummaryPage(
            @Valid @ModelAttribute(value = FundTransferSummary.Metadata.MODEL) FundTransferSummary fundTransferSummary,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("fundTransferSummary");
        }

        // Decide redirect page based on option (default to Welcome page)
        String redirectView;
        if (FundTransferSummary.Option.TRANSACTION.toString().equalsIgnoreCase(fundTransferSummary.getOption())) {
            redirectView = "transaction";
        } else {
            redirectView = "welcome";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
