package zz_Extra;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomInformation {

    public static String  randomName(){

        return RandomStringUtils.randomAlphabetic(5);
    }

    public static String randomNumber() {
        return RandomStringUtils.randomNumeric(5);
    }


}
