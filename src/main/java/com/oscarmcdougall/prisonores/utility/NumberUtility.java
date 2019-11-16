package com.oscarmcdougall.prisonores.utility;

import java.util.regex.Pattern;

public class NumberUtility {

    private static String Digits     = "(\\p{Digit}+)";
    private static String HexDigits  = "(\\p{XDigit}+)";
    private static String Exp        = "[eE][+-]?"+Digits;
    private static String fpRegex    =
            ("[\\x00-\\x20]*"+
                    "[+-]?(" +
                    "NaN|" +
                    "Infinity|" +
                    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
                    "(\\.("+Digits+")("+Exp+")?)|"+
                    "((" +
                    "(0[xX]" + HexDigits + "(\\.)?)|" +
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +
                    ")[pP][+-]?" + Digits + "))" +
                    "[fFdD]?))" +
                    "[\\x00-\\x20]*");

    public static boolean isDouble(String o) {
        return Pattern.matches(fpRegex, o);
    }

    public static boolean isInteger(String o) {
        return o.matches("-?\\d+");
    }
}