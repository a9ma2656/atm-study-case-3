package com.cdc.atm.web.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

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

    public static int getRandomNumberInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
