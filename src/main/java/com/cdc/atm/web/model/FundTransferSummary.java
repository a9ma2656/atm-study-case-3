package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

/**
 * Fund transfer summary model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class FundTransferSummary {

    public enum Option {
        TRANSACTION(1), EXIT(2);
        private int option;

        Option(int option) {
            this.option = option;
        }

        @Override
        public String toString() {
            return option == 0 ? EXIT.toString() : String.valueOf(option);
        }
    }

    public static class Metadata {
        public static final String MODEL = "fundTransferSummary";
    }

    private String accountNumber;
    private String transferAmount;
    private String referenceNumber;
    private String balance;

    @Size(min = 1, max = 1, message = "Invalid option")
    @Pattern(regexp = "[1-2]", message = "Invalid option")
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
