package com.cdc.atm.web.model;

import com.cdc.atm.web.validator.Max;
import com.cdc.atm.web.validator.Numeric;

/**
 * Other Withdraw model data object
 *
 * @author Made.AgusAdi@mitrais.com
 */
public class OtherWithdraw {

    public static class Metadata {
        public static final String MODEL = "otherWithdraw";
    }

    @Numeric(message = "Invalid amount", multiply = "10")
    @Max(message = "Maximum amount to withdraw is $1000", value = "1000")
    private String withdraw;

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }
}
