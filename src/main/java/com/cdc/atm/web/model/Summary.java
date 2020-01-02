package com.cdc.atm.web.model;

import com.cdc.atm.web.util.DateUtil;
import com.cdc.atm.web.util.NumericUtil;
import com.cdc.atm.web.validator.Pattern;
import com.cdc.atm.web.validator.Size;

import java.math.BigDecimal;
import java.util.Date;

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
    private String     option;

    private Date       date;
    private BigDecimal withdraw;
    private BigDecimal balance;

    public String getWebDate() {
        if (this.date != null) {
            return DateUtil.formatDateToString(this.date);
        }
        return "";
    }

    public String getWebWithdraw() {
        if (this.withdraw != null) {
            return NumericUtil.getPlainCurrencyFormat(this.withdraw);
        }
        return "";
    }

    public String getWebBalance() {
        if (this.balance != null) {
            return NumericUtil.getPlainCurrencyFormat(this.balance);
        }
        return "";
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Date getDate() {
        return date != null ? date : new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
