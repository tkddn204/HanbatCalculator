package ssangwoo.com.hanbatcalculator;

/**
 * Created by ssangwoo on 2017-09-15.
 */

enum Keypad {
    ONE(R.id.button_one, "1"),
    TWO(R.id.button_two, "2"),
    THREE(R.id.button_three, "3"),
    FOUR(R.id.button_four, "4"),
    FIVE(R.id.button_five, "5"),
    SIX(R.id.button_six, "6"),
    SEVEN(R.id.button_seven, "7"),
    EIGHT(R.id.button_eight, "8"),
    NINE(R.id.button_nine, "9"),
    ZERO(R.id.button_zero, "0"),

    REMOVE(R.id.button_remove, "C"),
    BRACKET(R.id.button_bracket, "("),
    BACK(R.id.button_back, "<"),

    PLUS(R.id.button_plus, "+"),
    MINUS(R.id.button_minus, "-"),
    MULTIPLY(R.id.button_mul, "*"),
    DIV(R.id.button_div, "/"),

    SIGN(R.id.button_sign, "+/-"),
    DOT(R.id.button_dot, "."),
    RESULT(R.id.button_result, "=");

    private final int id;
    private final String key;

    Keypad(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public static String[] getSignList() {
        String[] signs = {
                PLUS.getKey(),
                MINUS.getKey(),
                MULTIPLY.getKey(),
                DIV.getKey() };
        return signs;
    }
}
