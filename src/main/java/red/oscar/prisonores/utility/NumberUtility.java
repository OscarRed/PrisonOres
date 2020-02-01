package red.oscar.prisonores.utility;

import java.util.regex.Pattern;

public class NumberUtility {

    private String doubleRegex = "[0-9]{1,13}(\\\\.[0-9]*)?";
    private String integerRegex = "-?\\d+";

    public boolean isDouble(String inputString) {
        return Pattern.matches(doubleRegex, inputString);
    }

    public boolean isInteger(String inputString) {
        return Pattern.matches(integerRegex, inputString);
    }
}