package com.cdc.atm.web.component;

import org.springframework.stereotype.Component;

@Component
public class AccountComponent {
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
