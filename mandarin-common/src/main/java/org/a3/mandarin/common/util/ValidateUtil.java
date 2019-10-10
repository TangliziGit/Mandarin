package org.a3.mandarin.common.util;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
    private static Pattern phoneNumberPattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    private static EmailValidator emailValidator=EmailValidator.getInstance();

    public static Boolean validatePassword(String password){
        if (password.length()<2)
            return false;
        return true;
    }

    public static Boolean validatePhoneNumber(String phoneNumber){
        Matcher m = phoneNumberPattern.matcher(phoneNumber);
        return m.matches();
    }

    public static Boolean validateEmail(String email){
        return emailValidator.isValid(email);
    }

    public static Boolean validateName(String name){
        if (name.length()<2)
            return false;
        return true;
    }
    
    public static Boolean validateNumber(Double number){
        // TODO
        return true;
    }
}
