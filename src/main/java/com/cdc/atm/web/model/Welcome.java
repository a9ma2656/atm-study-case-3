package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

import javax.validation.constraints.NotBlank;

/**
 * Welcome model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class Welcome {
    public static class Metadata {
        public static final String MODEL = "welcome";
    }

    @NotBlank(message = "Account Number is required")
    @Size(min = 6, max = 6, message = "Account Number should have 6 digits")
    @Pattern(regexp = "^[0-9]+", message = "Account Number should only contains numbers")
    private String accountNumber;

    @NotBlank(message = "PIN is required")
    @Size(min = 6, max = 6, message = "PIN should have 6 digits length")
    @Pattern(regexp = "^[0-9]+", message = "PIN should only contains numbers")
    private String pin;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
