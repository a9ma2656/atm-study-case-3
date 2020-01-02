package com.cdc.atm.web.service;

import com.cdc.atm.web.model.Account;

public interface AccountService {
    /**
     * To validate user account and PIN
     *
     * @param accountNumber
     *            The account number
     * @param pin
     *            the PIN
     * @return flag in boolean to indicate the account and PIN is valid or not (true
     *         if valid)
     */
    boolean validateAccount(String accountNumber, String pin);

    /**
     * To get user account entity by account number
     * 
     * @param accountNumber
     *            The account number
     * @return Account entity
     */
    Account findByAccountNumber(String accountNumber);

    /**
     * To update user account entity
     *
     * @param account
     *            The account entity
     * @return Account entity
     */
    Account updateAccount(Account account);
}
