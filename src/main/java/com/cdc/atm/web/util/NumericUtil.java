package com.cdc.atm.web.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Numeric Utility class
 * 
 * @author Made.AgusAdi@mitrais.com
 */
public class NumericUtil {
    public static final String CURRENCY_FORMAT_PLAIN = "###,###";

    public static String getPlainCurrencyFormat(BigDecimal d) {
        return formatCurrency(d, CURRENCY_FORMAT_PLAIN);
    }

    public static String formatCurrency(BigDecimal v, String dec_format) {
        DecimalFormat df = new DecimalFormat(dec_format);
        if (v == null) {
            return "";
        }
        return (v.compareTo(new BigDecimal(0)) == -1) ? ("(" + df.format(v.abs()) + ")") : df.format(v);
    }
}
