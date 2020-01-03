package com.cdc.atm.web.service;

import com.cdc.atm.web.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implementation of {@link TransactionService}
 *
 * @author made.agusadi@mitrais.com
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

}
