package com.cdc.atm.web.service;

import com.cdc.atm.web.model.entity.Account;
import com.cdc.atm.web.model.entity.Transaction;
import com.cdc.atm.web.repository.AccountRepository;
import com.cdc.atm.web.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The implementation of {@link AccountService}
 *
 * @author made.agusadi@mitrais.com
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository     repository;
    private TransactionRepository trxRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, TransactionRepository trxRepository) {
        this.repository = repository;
        this.trxRepository = trxRepository;
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
        // Update user account balance
        Account account = repository.findByAccountNumber(accountNumber);
        BigDecimal newAccountBalance = account.getBalance().subtract(withdrawAmount);
        account.setBalance(newAccountBalance);
        Account updatedAccount = repository.save(account);

        // Log transaction (audit)
        Transaction trx = new Transaction();
        trx.setAccountNumber(accountNumber);
        trx.setTrxDate(new Date());
        trx.setTrxType(Transaction.TrxType.WITHDRAW.toString());
        trx.setBalance(updatedAccount.getBalance());
        trx.setAmount(withdrawAmount);
        trxRepository.save(trx);

        return updatedAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Account fundTransfer(String sourceAccountNumber, String destAccountNumber, BigDecimal transferAmount,
            String referenceNumber) {
        Account sourceAccount = repository.findByAccountNumber(sourceAccountNumber);
        Account destAccount = repository.findByAccountNumber(destAccountNumber);

        // Calculate new balance
        BigDecimal newSourceAccountBalance = sourceAccount.getBalance().subtract(transferAmount);
        BigDecimal newDestAccountBalance = destAccount.getBalance().add(transferAmount);

        sourceAccount.setBalance(newSourceAccountBalance);
        destAccount.setBalance(newDestAccountBalance);

        repository.save(destAccount);
        Account updatedSourceAccount = repository.save(sourceAccount);

        // Log transaction (audit)
        Transaction trx = new Transaction();
        trx.setAccountNumber(updatedSourceAccount.getAccountNumber());
        trx.setTrxDate(new Date());
        trx.setTrxType(Transaction.TrxType.FUND_TRANSFER.toString());
        trx.setBalance(updatedSourceAccount.getBalance());
        trx.setAmount(transferAmount);
        trx.setDestinationAccountNumber(destAccount.getAccountNumber());
        trx.setReferenceNumber(referenceNumber);
        trxRepository.save(trx);

        return updatedSourceAccount;
    }
}
