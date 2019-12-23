package com.cdc.atm.web.service;

import org.springframework.stereotype.Service;

/**
 * The implementation of WelcomeService
 *
 * @author made.agusadi@mitrais.com
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccount(String accountNumber, String pin) {
        return false;
    }
}
