package com.cdc.atm.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Utility class
 * 
 * @author Made.AgusAdi@mitrais.com
 */
public class DateUtil {
    public static final String DATETIME_12HRS_SHORT_FORMAT = "yyyy-MM-dd hh:mm a";

    public static String formatDateToString(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, null);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf;
        if (pattern != null) {
            sdf = new SimpleDateFormat(pattern);
        } else {
            sdf = new SimpleDateFormat(DATETIME_12HRS_SHORT_FORMAT);
        }
        sdf.setLenient(false);
        return sdf.format(date);
    }
}
