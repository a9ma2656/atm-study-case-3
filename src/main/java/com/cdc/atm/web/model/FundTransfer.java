package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.*;

/**
 * Fund transfer model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class FundTransfer {

    public enum Page {
        FUND_TRANSFER_PAGE_1("1"), FUND_TRANSFER_PAGE_2("2"), FUND_TRANSFER_PAGE_3("3"), FUND_TRANSFER_PAGE_4("4");
        private String page;

        Page(String page) {
            this.page = page;
        }

        @Override
        public String toString() {
            return page == null || "".equals(page) ? FUND_TRANSFER_PAGE_1.toString() : page;
        }
    }

    public enum Option {
        CONFIRM_TRX(1), CANCEL_TRX(2);
        private int option;

        Option(int option) {
            this.option = option;
        }

        @Override
        public String toString() {
            return option == 0 ? CANCEL_TRX.toString() : String.valueOf(option);
        }
    }

    public static class Metadata {
        public static final String MODEL = "fundTransfer";
    }

    private String page;

    @Size(min = 6, max = 6, message = "Invalid account")
    @Pattern(regexp = "^[0-9]+", message = "Invalid account")
    private String accountNumber;

    @Numeric(message = "Invalid amount")
    @Max(message = "Maximum amount to withdraw is $1000", value = "1000")
    @Min(message = "Minimum amount to withdraw is $1", value = "1")
    private String transferAmount;

    @Size(min = 6, max = 6, message = "Invalid Reference Number")
    @Numeric(message = "Invalid Reference Number")
    private String referenceNumber;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
