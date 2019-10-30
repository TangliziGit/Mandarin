package org.a3.mandarin.common.util;

public class StringUtil {

    public static String escapeHtmlTag(String content){
        return content.replaceAll("<.*?>", "");
    }
}
