package net.ukr.dreamsicle.validation;

import org.junit.Assert;
import org.junit.Test;

public class ValidEmailAddressTest {

    @Test
    public void isValidUniqueEmailAddress() {
        ValidEmailAddress validEmailAddress = new ValidEmailAddress();
        Assert.assertTrue(validEmailAddress.isValidUniqueEmailAddress("dreamsicle@ukr.net"));
    }
}