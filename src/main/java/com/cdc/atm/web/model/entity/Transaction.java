package com.cdc.atm.web.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction persistent object
 *
 * @author Made.AgusAdi@mitrais.com
 */
@Entity
@Table(name = "transaction")
public class Transaction {
    public enum TrxType {
        WITHDRAW("Withdraw"), FUND_TRANSFER("Fund Transfer");
        private String trxType;

        TrxType(String trxType) {
            this.trxType = trxType;
        }

        @Override
        public String toString() {
            return trxType;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long          id;

    @Column(name = "account_number")
    private String        accountNumber;

    @Column(name = "trx_date")
    private LocalDateTime trxDate;

    @Column(name = "trx_type")
    private String        trxType;

    @Column(name = "amount")
    private BigDecimal    amount;

    @Column(name = "balance")
    private BigDecimal    balance;

    @Column(name = "destination_account_number")
    private String        destinationAccountNumber;

    @Column(name = "reference_number")
    private String        referenceNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(LocalDateTime trxDate) {
        this.trxDate = trxDate;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
