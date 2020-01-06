package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.model.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    /**
     * To get user account last 10 transactions
     *
     * @param accountNumber
     *            The account number
     * @return list of transactions
     */
    List<Transaction> getLastTenTransaction(String accountNumber);

    /**
     * To get all user account today transactions
     *
     * @param accountNumber
     *            The account number
     * @return list of transactions
     */
    List<Transaction> getTodayTransaction(String accountNumber);

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
