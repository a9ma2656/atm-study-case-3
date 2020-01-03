package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Account;

import java.math.BigDecimal;

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

    /**
     * To perform fund withdraw
     * 
     * @param accountNumber
     *            the account number
     * @param withdrawAmount
     *            the withdraw amount
     * @return the updated account details
     */
    Account fundWithdraw(String accountNumber, BigDecimal withdrawAmount);

    /**
     * To perform fund transfer
     *
     * @param sourceAccountNumber
     *            the source account number
     * @param destAccountNumber
     *            the destination account number
     * @param transferAmount
     *            the transfer amount
     * @param referenceNumber
     *            the reference number
     * @return the updated source account details
     */
    Account fundTransfer(String sourceAccountNumber, String destAccountNumber, BigDecimal transferAmount,
            String referenceNumber);
}
