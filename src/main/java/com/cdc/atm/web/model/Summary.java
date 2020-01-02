package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

/**
 * Summary model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class Summary {
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
        public static final String MODEL = "summary";
    }

    @Size(min = 1, max = 1, message = "Option should be 1 digit length")
    @Pattern(regexp = "[1-2]", message = "Invalid option")
    private String option;

    private String date;
    private String withdraw;
    private String balance;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
