package com.cdc.atm.web.service;

import com.cdc.atm.web.model.Account;
import com.cdc.atm.web.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The implementation of WelcomeService
 *
 * @author made.agusadi@mitrais.com
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validateAccount(String accountNumber, String pin) {
        Account account = repository.validateAccount(accountNumber, pin);
        return account != null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Account findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account updateAccount(Account account) {
        return repository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account fundWithdraw(String accountNumber, BigDecimal withdrawAmount) {
        Account account = repository.findByAccountNumber(accountNumber);
        BigDecimal newAccountBalance = account.getBalance().subtract(withdrawAmount);
        account.setBalance(newAccountBalance);
        return repository.save(account);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account fundTransfer(String sourceAccountNumber, String destAccountNumber, BigDecimal transferAmount) {
        Account sourceAccount = repository.findByAccountNumber(sourceAccountNumber);
        Account destAccount = repository.findByAccountNumber(destAccountNumber);

        // Calculate new balance
        BigDecimal newSourceAccountBalance = sourceAccount.getBalance().subtract(transferAmount);
        BigDecimal newDestAccountBalance = destAccount.getBalance().add(transferAmount);

        sourceAccount.setBalance(newSourceAccountBalance);
        destAccount.setBalance(newDestAccountBalance);

        repository.save(destAccount);
        return repository.save(sourceAccount);
    }
}
