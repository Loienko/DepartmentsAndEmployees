package net.ukr.dreamsicle.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_EMAIL_ADDRESS;

public class ValidEmailAddress {
    private Pattern pattern;
    private Matcher matcher;

    public ValidEmailAddress() {
        pattern = Pattern.compile(REGEX_CHECK_VALID_EMAIL_ADDRESS);
    }

    public boolean isValidUniqueEmailAddress(String email) {
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
