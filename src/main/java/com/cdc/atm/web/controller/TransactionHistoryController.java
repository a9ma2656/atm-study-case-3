package com.cdc.atm.web.controller;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.TransactionHistory;
import com.cdc.atm.web.model.entity.Transaction;
import com.cdc.atm.web.service.TransactionService;
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
 * Provide controller for Transaction history screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class TransactionHistoryController {

    private TransactionService service;
    private AccountComponent   accountComponent;

    @Autowired
    public TransactionHistoryController(TransactionService service, AccountComponent accountComponent) {
        this.service = service;
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/transactionHistory")
    public ModelAndView getTransactionPage(
            @ModelAttribute(value = TransactionHistory.Metadata.MODEL) TransactionHistory transactionHistory) {
        if (accountComponent == null || accountComponent.getAccountNumber() == null
                || "".equals(accountComponent.getAccountNumber().trim())) {
            return new ModelAndView("redirect:/welcome");
        }

        return new ModelAndView("transactionHistory");
    }

    @PostMapping(value = "/transactionHistory")
    public ModelAndView postTransactionPage(
            @Valid @ModelAttribute(value = TransactionHistory.Metadata.MODEL) TransactionHistory transactionHistory,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("transactionHistory");
        }

        // Decide redirect page based on option (default to Welcome page)
        String redirectView;
        if (TransactionHistory.Option.LAST_10_TRX.toString().equalsIgnoreCase(transactionHistory.getOption())) {
            List<Transaction> lastTenTransactions = service.getLastTenTransaction(accountComponent.getAccountNumber());
            if (!lastTenTransactions.isEmpty()) {
                model.put("listTransactions", lastTenTransactions);
            }
            return new ModelAndView("transactionHistory", model);
        } else if (TransactionHistory.Option.TODAY_TRX.toString().equalsIgnoreCase(transactionHistory.getOption())) {
            List<Transaction> todayTransactions = service.getTodayTransaction(accountComponent.getAccountNumber());
            if (!todayTransactions.isEmpty()) {
                model.put("listTransactions", todayTransactions);
            }
            return new ModelAndView("transactionHistory", model);
        } else {
            redirectView = "transaction";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
