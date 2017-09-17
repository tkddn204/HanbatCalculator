package ssangwoo.com.hanbatcalculator;

/**
 * Created by ssangwoo on 2017-09-15.
 */

public enum SignKeypad {
    REMOVE(R.id.button_remove, 'C'),
    BRACKET(R.id.button_bracket, '('),
    BACK(R.id.button_back, '<'),

    PLUS(R.id.button_plus, '+'),
    MINUS(R.id.button_minus, '-'),
    MULTIPLY(R.id.button_mul, '*'),
    DIV(R.id.button_div, '/'),

    SIGN(R.id.button_sign, '!'),
    DOT(R.id.button_dot, '.'),
    RESULT(R.id.button_result, '=');

    private final int id;
    private final char sign;

    SignKeypad(int id, char sign) {
        this.id = id;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public char getSign() {
        return sign;
    }

    public static Character[] getSignList() {
        Character[] signs = {
                PLUS.getSign(),
                MINUS.getSign(),
                MULTIPLY.getSign(),
                DIV.getSign() };
        return signs;
    }

    public static String splitSignRegex() {
        return "(?=[\\(\\-])|(?<=[\\(])|(?=[+*/\\)])|(?<=[+*/\\)])";
    }
    public static String splitResultRegex() {
        return "(?=[-+*/\\(\\)])|(?<=[-+*/\\(\\)])";
    }

    @Override
    public String toString() {
        return "" + getSign();
    }
}
