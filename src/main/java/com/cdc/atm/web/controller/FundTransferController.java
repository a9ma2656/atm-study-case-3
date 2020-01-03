package com.cdc.atm.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.*;
import com.cdc.atm.web.model.entity.Account;
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
        String fundTransferView = "fundTransfer" + page;
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView(fundTransferView);
        }

        // Get account details
        Account account = service.findByAccountNumber(accountComponent.getAccountNumber());

        String redirectView;
        // Handling request per screen
        if (FundTransfer.Page.FUND_TRANSFER_PAGE_1.toString().equals(page)) {
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
                    return new ModelAndView(fundTransferView);
                }
                // For valid account number, redirect to Fund transfer screen 2
                return new ModelAndView("fundTransfer2");
            } else {
                // If no account number set, redirect to Transaction screen
                redirectView = "transaction";
            }
        } else if (FundTransfer.Page.FUND_TRANSFER_PAGE_2.toString().equals(page)) {
            if (fundTransfer.getTransferAmount() != null && !"".equals(fundTransfer.getTransferAmount().trim())) {
                // Validate insufficient balance
                BigDecimal transferAmount = new BigDecimal(fundTransfer.getTransferAmount());

                // Validate the balance is sufficient
                if (account.getBalance().compareTo(transferAmount) == -1) {
                    errors.add(String.format(Withdraw.ERROR_MESSAGE_INSUFFICIENT_BALANCE, transferAmount));
                }

                // Return to withdraw screen if the balance is insufficient
                if (!errors.isEmpty()) {
                    model.put("errors", errors);
                    return new ModelAndView(fundTransferView);
                }

                // Auto-generate reference number (random 6 digits number)
                fundTransfer.setReferenceNumber(String.valueOf(NumericUtil.getRandomNumberInts(111111, 999999)));
                model.addAttribute(FundTransfer.Metadata.MODEL, fundTransfer);
                return new ModelAndView("fundTransfer3", model);
            } else {
                // If no transfer amount set, redirect to Transaction screen
                redirectView = "transaction";
            }
        } else if (FundTransfer.Page.FUND_TRANSFER_PAGE_3.toString().equals(page)) {
            if (fundTransfer.getReferenceNumber() != null && !"".equals(fundTransfer.getReferenceNumber().trim())) {
                model.addAttribute(FundTransfer.Metadata.MODEL, fundTransfer);
                return new ModelAndView("fundTransfer4", model);
            } else {
                // If no reference number set, redirect to Transaction screen
                redirectView = "transaction";
            }
        } else if (FundTransfer.Page.FUND_TRANSFER_PAGE_4.toString().equals(page)) {
            if (FundTransfer.Option.CONFIRM_TRX.toString().equalsIgnoreCase(fundTransfer.getOption())) {
                // When the balance is sufficient, deduct the user balance
                BigDecimal transferAmount = new BigDecimal(fundTransfer.getTransferAmount());
                service.fundTransfer(account.getAccountNumber(), fundTransfer.getAccountNumber(), transferAmount,
                        fundTransfer.getReferenceNumber());

                // Populate withdraw summary details and go to Summary screen
                FundTransferSummary fundTransferSummary = new FundTransferSummary();
                fundTransferSummary.setAccountNumber(fundTransfer.getAccountNumber());
                fundTransferSummary.setBalance(NumericUtil.getPlainCurrencyFormat(account.getBalance()));
                fundTransferSummary.setTransferAmount(NumericUtil.getPlainCurrencyFormat(transferAmount));
                fundTransferSummary.setReferenceNumber(fundTransfer.getReferenceNumber());
                model.addAttribute(FundTransferSummary.Metadata.MODEL, fundTransferSummary);
                return new ModelAndView("fundTransferSummary", model);
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
