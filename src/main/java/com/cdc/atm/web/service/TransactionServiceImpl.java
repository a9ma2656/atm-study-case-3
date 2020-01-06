package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.model.entity.Transaction;
import com.cdc.atm.web.repository.AccountRepository;
import com.cdc.atm.web.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private AccountRepository     accountRepository;
    private TransactionRepository trxRepository;

    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository trxRepository) {
        this.accountRepository = accountRepository;
        this.trxRepository = trxRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getLastTenTransaction(String accountNumber) {
        return trxRepository.findTop10ByAccountNumberOrderByTrxDateDesc(accountNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
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
        return trxRepository.findTransactionByDate(accountNumber, startCal.getTime(), endCal.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account fundWithdraw(String accountNumber, BigDecimal withdrawAmount) {
        // Update user account balance
        Account account = accountRepository.findByAccountNumber(accountNumber);
        BigDecimal newAccountBalance = account.getBalance().subtract(withdrawAmount);
        account.setBalance(newAccountBalance);

        // Log transaction (audit)
        Transaction trx = new Transaction();
        trx.setAccountNumber(accountNumber);
        trx.setTrxDate(LocalDateTime.now());
        trx.setTrxType(Transaction.TrxType.WITHDRAW.toString());
        trx.setBalance(account.getBalance());
        trx.setAmount(withdrawAmount);

        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account fundTransfer(String sourceAccountNumber, String destAccountNumber, BigDecimal transferAmount,
            String referenceNumber) {
        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        Account destAccount = accountRepository.findByAccountNumber(destAccountNumber);

        // Calculate new balance
        BigDecimal newSourceAccountBalance = sourceAccount.getBalance().subtract(transferAmount);
        BigDecimal newDestAccountBalance = destAccount.getBalance().add(transferAmount);

        sourceAccount.setBalance(newSourceAccountBalance);
        destAccount.setBalance(newDestAccountBalance);

        // Log transaction (audit)
        Transaction trx = new Transaction();
        trx.setAccountNumber(sourceAccount.getAccountNumber());
        trx.setTrxDate(LocalDateTime.now());
        trx.setTrxType(Transaction.TrxType.FUND_TRANSFER.toString());
        trx.setBalance(sourceAccount.getBalance());
        trx.setAmount(transferAmount);
        trx.setDestinationAccountNumber(destAccount.getAccountNumber());
        trx.setReferenceNumber(referenceNumber);
        trxRepository.save(trx);

        return sourceAccount;
    }
}
