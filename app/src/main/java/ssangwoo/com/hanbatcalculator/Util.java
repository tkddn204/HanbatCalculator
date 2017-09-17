package ssangwoo.com.hanbatcalculator;

import java.util.Arrays;

/**
 * Created by ssangwoo on 2017-09-17.
 */

class Util {
    public Util() {}

    static boolean isNumeric(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    static boolean endsWithSigns(String text) {
        return Arrays.asList(Keypad.getSignList())
                .contains(text.substring(text.length() - 1));
    }

    static boolean endsWithOpenBracket(String text) {
        return text.endsWith("(");
    }

    static boolean endsWithCloseBracket(String text) {
        return text.endsWith(")");
    }


}
