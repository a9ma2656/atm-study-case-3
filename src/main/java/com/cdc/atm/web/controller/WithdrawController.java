package com.cdc.atm.web.controller;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.model.Summary;
import com.cdc.atm.web.model.Withdraw;
import com.cdc.atm.web.service.AccountService;
import com.cdc.atm.web.util.DateUtil;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provide controller for Withdraw screen
 * 
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class WithdrawController {

    private AccountService   service;
    private AccountComponent accountComponent;

    @Autowired
    public WithdrawController(AccountService service, AccountComponent accountComponent) {
        this.service = service;
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/withdraw")
    public ModelAndView getWithdrawPage(@ModelAttribute(value = Withdraw.Metadata.MODEL) Withdraw withdraw) {
        if (accountComponent == null || accountComponent.getAccountNumber() == null
                || "".equals(accountComponent.getAccountNumber().trim())) {
            return new ModelAndView("redirect:/welcome");
        }
        return new ModelAndView("withdraw");
    }

    @PostMapping(value = "/withdraw")
    public ModelAndView postWithdrawPage(@Valid @ModelAttribute(value = Withdraw.Metadata.MODEL) Withdraw withdraw,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("withdraw");
        }

        // Decide redirect page based on option (default to Transaction screen)
        String redirectView;
        if (Withdraw.Option.DEDUCT_TEN_DOLLARS.toString().equalsIgnoreCase(withdraw.getOption())
                || Withdraw.Option.DEDUCT_FIFTY_DOLLARS.toString().equalsIgnoreCase(withdraw.getOption())
                || Withdraw.Option.DEDUCT_HUNDRED_DOLLARS.toString().equalsIgnoreCase(withdraw.getOption())) {
            // Check insufficient Balance
            String accountNumber = accountComponent.getAccountNumber();
            Account account = service.findByAccountNumber(accountNumber);

            // Get the selected withdraw
            BigDecimal withdrawAmount;
            if (Withdraw.Option.DEDUCT_TEN_DOLLARS.toString().equalsIgnoreCase(withdraw.getOption())) {
                withdrawAmount = new BigDecimal(10);
            } else if (Withdraw.Option.DEDUCT_FIFTY_DOLLARS.toString().equalsIgnoreCase(withdraw.getOption())) {
                withdrawAmount = new BigDecimal(50);
            } else {
                withdrawAmount = new BigDecimal(100);
            }

            // Validate the balance is sufficient
            if (account.getBalance().compareTo(withdrawAmount) == -1) {
                errors.add(String.format(Withdraw.ERROR_MESSAGE_INSUFFICIENT_BALANCE, withdrawAmount));
            }

            // Return to withdraw screen if the balance is insufficient
            if (!errors.isEmpty()) {
                model.put("errors", errors);
                return new ModelAndView("withdraw");
            }

            // When the balance is sufficient, deduct the user account balance
            service.fundWithdraw(account.getAccountNumber(), withdrawAmount);

            // Populate withdraw summary details and go to Summary screen
            Summary summary = new Summary();
            summary.setDate(DateUtil.formatDateToString(new Date()));
            summary.setBalance(NumericUtil.getPlainCurrencyFormat(account.getBalance()));
            summary.setWithdraw(NumericUtil.getPlainCurrencyFormat(withdrawAmount));
            model.addAttribute(Summary.Metadata.MODEL, summary);
            return new ModelAndView("summary", model);
        } else if (Withdraw.Option.OTHER.toString().equalsIgnoreCase(withdraw.getOption())) {
            redirectView = "otherWithdraw";
        } else {
            redirectView = "transaction";
        }

        return new ModelAndView("redirect:/" + redirectView);
    }
}
