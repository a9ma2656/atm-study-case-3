package com.cdc.atm.web.service;

public interface WelcomeService {
    /**
     * To validate user account and PIN
     *
     * @param accountNumber The account number
     * @param pin           the PIN
     * @return flag in boolean to indicate the account and PIN is valid or not (true if valid)
     */
    boolean validateAccount(String accountNumber, String pin);
}
