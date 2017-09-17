package ssangwoo.com.hanbatcalculator;

/**
 * Created by ssangwoo on 2017-09-15.
 */

public enum NumberKeypad {
    ONE(R.id.button_one, 1),
    TWO(R.id.button_two, 2),
    THREE(R.id.button_three, 3),
    FOUR(R.id.button_four, 4),
    FIVE(R.id.button_five, 5),
    SIX(R.id.button_six, 6),
    SEVEN(R.id.button_seven, 7),
    EIGHT(R.id.button_eight, 8),
    NINE(R.id.button_nine, 9),
    ZERO(R.id.button_zero, 0);

    private final int id;
    private final int number;

    NumberKeypad(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

}
