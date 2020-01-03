package com.cdc.atm.web.controller;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide controller for Transaction screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class TransactionController {

    private AccountComponent accountComponent;

    @Autowired
    public TransactionController(AccountComponent accountComponent) {
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/transaction")
    public ModelAndView getTransactionPage(@ModelAttribute(value = Transaction.Metadata.MODEL) Transaction account) {
        if (accountComponent == null || accountComponent.getAccountNumber() == null
                || "".equals(accountComponent.getAccountNumber().trim())) {
            return new ModelAndView("redirect:/welcome");
        }
        return new ModelAndView("transaction");
    }

    @PostMapping(value = "/transaction")
    public ModelAndView postTransactionPage(
            @Valid @ModelAttribute(value = Transaction.Metadata.MODEL) Transaction transaction, BindingResult result,
            ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("transaction");
        }

        // Decide redirect page based on option (default to Welcome page)
        String redirectView;
        if (Transaction.Option.WITHDRAW.toString().equalsIgnoreCase(transaction.getOption())) {
            redirectView = "withdraw";
        } else if (Transaction.Option.FUND_TRANSFER.toString().equalsIgnoreCase(transaction.getOption())) {
            redirectView = "fundTransfer";
        } else {
            redirectView = "welcome";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
