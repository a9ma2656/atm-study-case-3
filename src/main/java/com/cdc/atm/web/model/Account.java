package com.cdc.atm.web.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Size(min = 6, max = 6, message = "Account Number should have 6 digits")
    @Pattern(regexp = "^[0-9]+", message = "Account Number should only contains numbers")
    @Column(name = "account_number")
    private String accountNumber;

    @Size(max = 100, message = "Name should not exceed 100 characters")
    @Column(name = "name")
    private String name;

    @Size(min = 6, max = 6, message = "PIN should have 6 digits length")
    @Pattern(regexp = "^[0-9]+", message = "PIN should only contains numbers")
    @Column(name = "pin")
    private String pin;

    @Column(name = "balance")
    private BigDecimal balance;

    public static class Metadata {
        public static final String MODEL = "account";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
