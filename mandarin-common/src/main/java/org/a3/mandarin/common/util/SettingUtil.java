package org.a3.mandarin.common.util;

public class SettingUtil {
    public static final String FINE="FINE";
    public static final String PERIOD="PERIOD";
    public static final String DEPOSIT="DEPOSIT";

    public static String convertToSettingName(String name){
        if (FINE.equalsIgnoreCase(name))
            return FINE;
        if (PERIOD.equalsIgnoreCase(name))
            return PERIOD;
        if (DEPOSIT.equalsIgnoreCase(name))
            return DEPOSIT;
        return null;
    }
}
