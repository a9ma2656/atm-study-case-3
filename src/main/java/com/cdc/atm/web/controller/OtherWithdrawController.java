package com.cdc.atm.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.cdc.atm.web.component.AccountComponent;
import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.model.OtherWithdraw;
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

/**
 * Provide controller for Other Withdraw screen
 *
 * @author Made.AgusaAdi@mitrais.com
 */
@Controller
public class OtherWithdrawController {

    private AccountService   service;
    private AccountComponent accountComponent;

    @Autowired
    public OtherWithdrawController(AccountService service, AccountComponent accountComponent) {
        this.service = service;
        this.accountComponent = accountComponent;
    }

    @GetMapping(value = "/otherWithdraw")
    public ModelAndView getOtherWithdrawPage(
            @ModelAttribute(value = OtherWithdraw.Metadata.MODEL) OtherWithdraw otherWithdraw) {
        return new ModelAndView("otherWithdraw");
    }

    @PostMapping(value = "/otherWithdraw")
    public ModelAndView postOtherWithdrawPage(
            @Valid @ModelAttribute(value = OtherWithdraw.Metadata.MODEL) OtherWithdraw otherWithdraw,
            BindingResult result, ModelMap model) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.put("errors", errors);
            return new ModelAndView("otherWithdraw");
        }

        // When the amount to withdraw is not set, redirect to transaction screen
        if (otherWithdraw.getWithdraw() == null || "".equals(otherWithdraw.getWithdraw())) {
            return new ModelAndView("redirect:/transaction");
        }

        // Check insufficient Balance
        String accountNumber = accountComponent.getAccountNumber();
        Account account = service.findByAccountNumber(accountNumber);

        BigDecimal withdrawAmount = new BigDecimal(otherWithdraw.getWithdraw());
        // Validate the balance is sufficient
        if (account.getBalance().compareTo(withdrawAmount) == -1) {
            errors.add(String.format(Withdraw.ERROR_MESSAGE_INSUFFICIENT_BALANCE, withdrawAmount));
        }

        // Return to otherWithdraw screen if the balance is insufficient
        if (!errors.isEmpty()) {
            model.put("errors", errors);
            return new ModelAndView("otherWithdraw");
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
    }
}
