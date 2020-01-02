package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

/**
 * Transaction model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class Transaction {
    public enum Option {
        WITHDRAW(1), FUND_TRANSFER(2), EXIT(3);
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
        public static final String MODEL = "transaction";
    }

    @Size(min = 1, max = 1, message = "Option should be 1 digit length")
    @Pattern(regexp = "[1-3]", message = "Invalid option")
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
