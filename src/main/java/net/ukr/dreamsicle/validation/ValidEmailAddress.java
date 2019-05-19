package net.ukr.dreamsicle.validation;

import java.util.regex.Pattern;

import static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_EMAIL_ADDRESS;

public class ValidEmailAddress {
    private Pattern pattern;

    public ValidEmailAddress() {
        pattern = Pattern.compile(REGEX_CHECK_VALID_EMAIL_ADDRESS);
    }

    public boolean isValidUniqueEmailAddress(String email) {
        return pattern.matcher(email).matches();
    }
}
