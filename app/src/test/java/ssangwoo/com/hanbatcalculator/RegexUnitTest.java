package ssangwoo.com.hanbatcalculator;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegexUnitTest {
    String text;
    @Before
    public void beforeTextTest() throws Exception {
        //text = "6123+(-125-(123+(-532)))*(-1)+(-532+(-562))";
        //text = "(-125*(352)+(-122)-(-75-(6*643)-2)-46)*(-35)";
        //text = "125*543/(-635+36)+3";
        //text = "5231-325+325-(-53+25-(-25)-64+(-364))";
        text = "1+5=";
    }

    @Test
    public void addition_isCorrect() throws Exception {
        //String[] splitText = text.split(SignKeypad.splitSignRegex());
        String[] splitText = text.split("(?=[^(\\-\\d+])");
        for(String s: splitText) {
            System.out.println(s);
        }
    }

    @Test
    public void minus_isCorrect() throws Exception {
        String[] splitText = text.split("(?<=[^(\\-\\d$)])|(?=[+*/\\)])|(?<=[+*/\\)])");
        for(String s: splitText) {
            System.out.println(s);
        }

    }

    @Test
    public void England_isCorrect() throws Exception {
        String[] splitText = text.split("(?=[+-/*=])|(?<=[+-/*=])");
        for(String s: splitText) {
            System.out.println(s);
        }

    }
}