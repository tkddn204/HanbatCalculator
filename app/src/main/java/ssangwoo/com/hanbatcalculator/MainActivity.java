package ssangwoo.com.hanbatcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView textResult;
    Button[] btnKeypad;

    private String bufferText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initLayout();
    }

    private void init() {
        btnKeypad = new Button[
                NumberKeypad.values().length + SignKeypad.values().length];
        bufferText = "";
    }

    private void initLayout() {
        textResult = (TextView) findViewById(R.id.text_result);
        makeKeypadButtons();
    }

    private void makeKeypadButtons() {
        int index = 0;
        for (final NumberKeypad item : NumberKeypad.values()) {
            btnKeypad[index] = (Button) findViewById(item.getId());
            btnKeypad[index].setText(String.valueOf(item.getNumber()));
            btnKeypad[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (endsWithCloseBracket()) {
                        bufferText += SignKeypad.MULTIPLY.getSign();
                    }
                    bufferText += item.getNumber();
                    textResult.setText(bufferText);
                }
            });
            index++;
        }

        for (final SignKeypad item : SignKeypad.values()) {
            btnKeypad[index] = (Button) findViewById(item.getId());
            btnKeypad[index].setText(String.valueOf(item.getSign()));
            btnKeypad[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferText = checkBufferAndPutSign(item);
                    textResult.setText(bufferText);
                    if (item.equals(SignKeypad.RESULT)) {
                        bufferText = "";
                    }
                }
            });
            index++;
        }
    }

    private boolean endsWithSigns(String text) {
        return Arrays.asList(SignKeypad.getSignList())
                .contains(text.charAt(text.length() - 1));
    }

    private boolean endsWithOpenBracket() {
        return bufferText.endsWith("(");
    }

    private boolean endsWithCloseBracket() {
        return bufferText.endsWith(")");
    }

    private boolean isNumeric(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    boolean isOpened;
    int bracketCount = 0;

    private String checkBufferAndPutSign(SignKeypad item) {
        String text = bufferText;
        switch (item) {
            // First line
            case REMOVE:
                text = "";
                bracketCount = 0;
                break;
            case BRACKET:
                if (bracketCount > 0) {
                    if (isNumeric(text.substring(text.length() - 1))
                            || endsWithCloseBracket()) {
                        text += ")";
                        bracketCount--;
                        break;
                    }
                }
                if (text.isEmpty() || endsWithSigns(text) || endsWithOpenBracket()) {
                    text += item.getSign();
                } else {
                    text += SignKeypad.MULTIPLY.getSign() + "" + item.getSign();
                }
                bracketCount++;
                break;
            case BACK:
                if (!text.isEmpty()) {
                    if (endsWithOpenBracket()) {
                        bracketCount--;
                    } else if (endsWithCloseBracket()) {
                        bracketCount++;
                    }
                    text = new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
                }
                break;

            // Second line
            case PLUS:
            case MULTIPLY:
            case DIV:
                if (text.isEmpty() || endsWithOpenBracket()) {
                    break;
                } else if (text.endsWith("(-")) {
                    text = new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
                    break;
                }
            case MINUS:
                if (endsWithOpenBracket()) {
                    text += item.getSign();
                }
                if (!text.isEmpty()) {
                    if (endsWithSigns(text)) {
                        text = new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
                    }
                    text += item.getSign();
                }
                break;

            // Last line
            case SIGN:
                if (text.isEmpty()) {
                    text += "(" + SignKeypad.MINUS.getSign();
                    bracketCount++;
                } else {
                    if (text.endsWith("(-")) {
                        text = new StringBuilder(text)
                                .delete(text.length() - 2, text.length()).toString();
                        bracketCount--;
                    } else if (endsWithOpenBracket() || endsWithSigns(text)) {
                        text += "(-";
                        bracketCount++;
                    } else if (endsWithCloseBracket()) {
                        text += "*(-";
                        bracketCount++;
                    } else {
                        String[] splitText = text.split(SignKeypad.splitSignRegex());
                        int splitTextSubtractIndex = splitText.length - 1;
                        double tempLastNumber = Double.parseDouble(splitText[splitTextSubtractIndex]);
                        if (tempLastNumber <= 0) {
                            if (splitTextSubtractIndex > 0) {
                                if (splitText[splitTextSubtractIndex - 1].equals("(")) {
                                    splitText[splitTextSubtractIndex] =
                                            splitText[splitTextSubtractIndex].substring(1);
                                    splitText[splitTextSubtractIndex - 1] = "";
                                    bracketCount--;
                                } else {
                                    splitText[splitTextSubtractIndex] =
                                            "-(" + splitText[splitTextSubtractIndex];
                                    bracketCount++;
                                }
                            } else {
                                splitText[splitTextSubtractIndex]
                                        = "(-" + splitText[splitTextSubtractIndex];
                                bracketCount++;
                            }
                        } else {
                            splitText[splitTextSubtractIndex]
                                    = "(-" + splitText[splitTextSubtractIndex];
                            bracketCount++;
                        }
                        StringBuilder textBuilder = new StringBuilder();
                        for (String string : splitText) {
                            textBuilder.append(string);
                        }
                        text = textBuilder.toString();
                    }
                }
                break;
            case DOT:
                if (text.isEmpty() || endsWithSigns(text) || endsWithOpenBracket()) {
                    text += "0" + item.getSign();
                } else if (endsWithCloseBracket()) {
                    text += SignKeypad.MULTIPLY + "0" + item.getSign();
                } else {
                    String[] splitText = text.split(SignKeypad.splitSignRegex());
                    if (!splitText[splitText.length - 1].contains(item.toString())) {
                        text += item.getSign();
                    }
                }
                break;
            case RESULT:
                if (text.isEmpty()) {
                    break;
                }
                if (endsWithSigns(text)) {
                    text = new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
                }
                if (bracketCount > 0) {
                    for (; bracketCount > 0; bracketCount--) {
                        text += ")";
                    }
                }

                String[] splitText = text.split(SignKeypad.splitSignRegex());
                if (text.contains("-")) {
                    List<String> tempSplitList = new ArrayList<>(Arrays.asList(splitText));
                    if (tempSplitList.size() > 1) {
                        for (int i = 1; i < tempSplitList.size(); i++) {
                            if (isNumeric(tempSplitList.get(i))) {
                                if (Double.parseDouble(tempSplitList.get(i)) < 0) {
                                    if (isNumeric(tempSplitList.get(i - 1))) {
                                        tempSplitList.add(i, "+");
                                    }
                                }
                            }
                        }
                    }
                    splitText = tempSplitList.toArray(new String[0]);
                }
                String result = calculate(splitText);
                text += item.getSign() + result;
                break;
            default:
                break;
        }
        return text;
    }

    private boolean isLowerPriority(String left, String right) {
        switch (left) {
            case "+":
                return !(right.equals("+") || right.equals("("));
            case "-":
                return !(right.equals("-") || right.equals("("));
            case "*":
                return right.equals("/") || right.equals("(");
            case "/":
                return right.equals("*") || right.equals("(");
            case "(":
                return false;
            default:
                return false;
        }
    }

    private String calculate(String[] splitText) {
        Stack<String> signList = new Stack<>();
        ArrayList<String> postfix = new ArrayList<>();

        try {
            for (String string : splitText) {
                if (isNumeric(string)) {
                    postfix.add(string);
                } else if (string.equals(")")) {
                    while (!signList.isEmpty() && !signList.peek().equals("(")) {
                        postfix.add(signList.pop());
                    }
                    if(!signList.isEmpty()) {
                        signList.pop();
                    }
                } else if (string.equals("(")) {
                    signList.push(string);
                } else {
                    if (!signList.isEmpty() && !isLowerPriority(string, signList.peek())) {
                        signList.push(string);
                    } else {
                        while (!signList.isEmpty() && isLowerPriority(string, signList.peek())) {
                            if(!signList.peek().equals("(")) {
                                postfix.add(signList.pop());
                            } else {
                                break;
                            }
                        }
                        signList.push(string);
                    }
                }
            }

            while(!signList.isEmpty()) {
                postfix.add(signList.pop());
            }

            Stack<Double> numberList = new Stack<>();
            double number1, number2;
            // postfix 계산하기
            if(postfix.size() > 2) {
                for (String string : postfix) {
                    if (isNumeric(string)) {
                        numberList.push(Double.parseDouble(string));
                    } else {
                        if(numberList.size() > 1) {
                            number1 = numberList.pop();
                            number2 = numberList.pop();
                            switch (string) {
                                case "+":
                                    numberList.push(number1 + number2);
                                    break;
                                case "-":
                                    numberList.push(number1 - number2);
                                    break;
                                case "*":
                                    numberList.push(number1 * number2);
                                    break;
                                case "/":
                                    numberList.push(number2 / number1);
                                    break;
                                default:
                                    numberList.push(number1);
                                    break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if(!numberList.isEmpty()) {
                    Double result = numberList.pop();
                    if (result == 0.) {
                        return "0";
                    } else if(result.longValue() >= Long.MAX_VALUE ||
                            result.longValue() <= Long.MIN_VALUE) {
                        return result.toString();
                    } else if ((result == Math.floor(result)) && !Double.isInfinite(result)) {
                        return String.valueOf(result.longValue());
                    } else {
                        return result.toString();
                    }
                } else {
                    return "오류 발생!";
                }

            } else {
                return postfix.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "올바른 식이 아닙니다!";
        }
        // return Arrays.toString(splitText);
    }
}
