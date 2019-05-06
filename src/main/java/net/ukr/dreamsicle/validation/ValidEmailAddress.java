package net.ukr.dreamsicle.validation;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_EMAIL_ADDRESS;

public class ValidEmailAddress {
    private Pattern pattern;
    private Matcher matcher;

    public ValidEmailAddress() {
        pattern = Pattern.compile(REGEX_CHECK_VALID_EMAIL_ADDRESS);
    }

    @Test
    public static void main(String[] args) {
        System.out.println(new ValidEmailAddress().isValidEmailAddress("dreamsicle@ukr.net"));

    }

    public boolean isValidEmailAddress(String email) {
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
