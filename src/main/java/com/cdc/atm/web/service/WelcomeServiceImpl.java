package com.cdc.atm.web.service;

import com.cdc.atm.web.model.Account;
import com.cdc.atm.web.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implementation of WelcomeService
 *
 * @author made.agusadi@mitrais.com
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

    private AccountRepository repository;

    @Autowired
    public WelcomeServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccount(String accountNumber, String pin) {
        Account account = repository.validateAccount(accountNumber, pin);
        System.out.println(account != null ? account.getBalance(): "Account is null");
        return account != null;
    }
}
