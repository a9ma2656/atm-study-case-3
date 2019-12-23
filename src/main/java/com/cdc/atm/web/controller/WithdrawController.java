package com.cdc.atm.web.controller;

import com.cdc.atm.web.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WithdrawController {

    @GetMapping(value = "/withdraw")
    public String getWithdrawPage(@ModelAttribute(value = Account.Metadata.MODEL) Account account) {
        return "withdraw";
    }
}
