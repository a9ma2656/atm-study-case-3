package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Transaction;

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
}
