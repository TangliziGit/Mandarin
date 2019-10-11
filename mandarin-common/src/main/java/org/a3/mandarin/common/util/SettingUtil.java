package org.a3.mandarin.common.util;

public class SettingUtil {
    public static final String FINE="FINE";
    public static final String BOOK_RETURN_PERIOD ="BOOK_RETURN_PERIOD";
    public static final String DEPOSIT="DEPOSIT";

    public static String convertToSettingName(String name){
        if (FINE.equalsIgnoreCase(name))
            return FINE;
        if (BOOK_RETURN_PERIOD.equalsIgnoreCase(name))
            return BOOK_RETURN_PERIOD;
        if (DEPOSIT.equalsIgnoreCase(name))
            return DEPOSIT;
        return null;
    }
}
