package com.cdc.atm.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.*;
import com.cdc.atm.web.service.AccountService;
import com.cdc.atm.web.util.NumericUtil;
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
 * Provide controller for Fund Transfer screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class FundTransferController {

    private AccountService   service;
    private AccountComponent accountComponent;

    @Autowired
    public FundTransferController(AccountService service, AccountComponent accountComponent) {
        this.service = service;
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/fundTransfer")
    public ModelAndView getFundTransferPage(
            @ModelAttribute(value = FundTransfer.Metadata.MODEL) FundTransfer fundTransfer) {
        return new ModelAndView("fundTransfer1");
    }

    @PostMapping(value = "/fundTransfer")
    public ModelAndView postFundTransferPage(
            @Valid @ModelAttribute(value = FundTransfer.Metadata.MODEL) FundTransfer fundTransfer, BindingResult result,
            ModelMap model) {
        List<String> errors = new ArrayList<>();
        String page = fundTransfer.getPage();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("fundTransfer" + page);
        }

        // Get account details
        Account account = service.findByAccountNumber(accountComponent.getAccountNumber());

        String redirectView;
        // Handling request per screen
        if ("1".equals(page)) {
            if (fundTransfer.getAccountNumber() != null && !"".equals(fundTransfer.getAccountNumber().trim())) {
                // Validate account exists and does not equal to source account
                Account targetAccount = service.findByAccountNumber(fundTransfer.getAccountNumber());
                if (targetAccount == null
                        || accountComponent.getAccountNumber().equals(targetAccount.getAccountNumber())) {
                    errors.add("Invalid account");
                }
                // For invalid account number, display error message on Fund transfer screen 1
                if (!errors.isEmpty()) {
                    model.put("errors", errors);
                    return new ModelAndView("fundTransfer" + page);
                }
                // For valid account number, redirect to Fund transfer screen 2
                redirectView = "fundTransfer2";
            } else {
                // If no account number set, redirect to Transaction screen
                redirectView = "transaction";
            }
        } else if ("2".equals(page)) {
            // Validate insufficient balance
            BigDecimal transferAmount = new BigDecimal(fundTransfer.getTransferAmount());

            // Validate the balance is sufficient
            if (account.getBalance().compareTo(transferAmount) == -1) {
                errors.add(String.format(Withdraw.ERROR_MESSAGE_INSUFFICIENT_BALANCE, transferAmount));
            }

            // Return to withdraw screen if the balance is insufficient
            if (!errors.isEmpty()) {
                model.put("errors", errors);
                return new ModelAndView("fundTransfer2");
            }

            redirectView = "fundTransfer3";
        } else if ("3".equals(page)) {
            redirectView = "fundTransfer4";
        } else if ("4".equals(page)) {
            if (FundTransfer.Option.CONFIRM_TRX.toString().equalsIgnoreCase(fundTransfer.getOption())) {
                // When the balance is sufficient, deduct the user balance
                BigDecimal transferAmount = new BigDecimal(fundTransfer.getTransferAmount());
                BigDecimal balance = account.getBalance();
                BigDecimal newBalance = balance.subtract(transferAmount);
                account.setBalance(newBalance);
                service.updateAccount(account);

                // Populate withdraw summary details and go to Summary screen
                FundTransferSummary summary = new FundTransferSummary();
                summary.setAccountNumber(fundTransfer.getAccountNumber());
                summary.setBalance(NumericUtil.getPlainCurrencyFormat(account.getBalance()));
                summary.setTransferAmount(NumericUtil.getPlainCurrencyFormat(transferAmount));
                summary.setReferenceNumber(fundTransfer.getReferenceNumber());
                model.put(Summary.Metadata.MODEL, summary);
                return new ModelAndView("redirect:/fundTransferSummary", model);
            } else {
                // Cancel fund transfer and redirect to transaction screen
                redirectView = "transaction";
            }
        } else {
            redirectView = "transaction";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
