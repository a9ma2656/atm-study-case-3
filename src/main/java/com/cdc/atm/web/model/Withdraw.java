package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

public class Withdraw {
    public enum WithdrawOption {
        DEDUCT_TEN_DOLLARS(1), DEDUCT_FIFTY_DOLLARS(2), DEDUCT_HUNDRED_DOLLARS(3), OTHER(4), BACK(5);
        private int option;

        WithdrawOption(int option) {
            this.option = option;
        }

        @Override
        public String toString() {
            return option == 0 ? BACK.toString() : String.valueOf(option);
        }
    }

    public static class Metadata {
        public static final String MODEL = "withdraw";
    }

    @Size(min = 1, max = 1, message = "Option should be 1 digit length")
    @Pattern(regexp = "[1-5]", message = "Invalid option")
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
