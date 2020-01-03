package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Transaction;
import com.cdc.atm.web.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getLastTenTransaction(String accountNumber) {
        return repository.findLastTransaction(accountNumber, 10);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTodayTransaction(String accountNumber) {
        Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCal.set(Calendar.HOUR_OF_DAY, startCal.getActualMinimum(Calendar.HOUR_OF_DAY));
        startCal.set(Calendar.MINUTE, startCal.getActualMinimum(Calendar.MINUTE));
        startCal.set(Calendar.SECOND, startCal.getActualMinimum(Calendar.SECOND));
        startCal.set(Calendar.MILLISECOND, startCal.getActualMinimum(Calendar.MILLISECOND));

        Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endCal.set(Calendar.HOUR_OF_DAY, startCal.getActualMaximum(Calendar.HOUR_OF_DAY));
        endCal.set(Calendar.MINUTE, startCal.getActualMaximum(Calendar.MINUTE));
        endCal.set(Calendar.SECOND, startCal.getActualMaximum(Calendar.SECOND));
        endCal.set(Calendar.MILLISECOND, startCal.getActualMaximum(Calendar.MILLISECOND));
        return repository.findTransactionByDate(accountNumber, startCal.getTime(), endCal.getTime());
    }
}
