package ssangwoo.com.hanbatcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ssangwoo.com.hanbatcalculator.Util.endsWithCloseBracket;
import static ssangwoo.com.hanbatcalculator.Util.endsWithOpenBracket;
import static ssangwoo.com.hanbatcalculator.Util.endsWithSigns;
import static ssangwoo.com.hanbatcalculator.Util.isNumeric;

/**
 * Created by ssangwoo on 2017-09-17.
 */

class ControlText {
    private static final String SPLIT_SIGN_REGEX =
            "(?=[\\(\\-])|(?<=[\\(])|(?=[+*/\\)])|(?<=[+*/\\)])";

    private int bracketCount;

    ControlText() {
        bracketCount = 0;
    }

    String checkBufferAndPutNumber(String bufferText, Keypad key) {
        String text = bufferText;
        if (endsWithCloseBracket(bufferText)) {
            text += "*";
        }
        text += key.getKey();
        return text;
    }

    String checkBufferAndPutSign(String bufferText, Keypad key) {
        String text = bufferText;
        switch (key) {
            // 모두 지우기(C)
            case REMOVE:
                text = "";
                bracketCount = 0;
                break;

            // 괄호
            case BRACKET:
                if (bracketCount > 0) {
                    if (isNumeric(text.substring(text.length() - 1))
                            || endsWithCloseBracket(text)) {
                        text += ")";
                        bracketCount--;
                        break;
                    }
                }
                if (text.isEmpty() || endsWithSigns(text) || endsWithOpenBracket(text)) {
                    text += key.getKey();
                } else {
                    text += "*" + key.getKey();
                }
                bracketCount++;
                break;

            // 한 글자 지우기(<)
            case BACK:
                if (!text.isEmpty()) {
                    if (endsWithOpenBracket(text)) {
                        bracketCount--;
                    } else if (endsWithCloseBracket(text)) {
                        bracketCount++;
                    }
                    text = removeLastIndex(text);
                }
                break;

            // 사칙연산
            case PLUS:
            case MULTIPLY:
            case DIV:
                if (text.isEmpty() || endsWithOpenBracket(text)) {
                    break;
                } else if (text.endsWith("(-")) {
                    text = removeLastIndex(text);
                    break;
                }
            case MINUS:
                if (endsWithOpenBracket(text)) {
                    text += key.getKey();
                }
                if (!text.isEmpty()) {
                    if (endsWithSigns(text)) {
                        text = removeLastIndex(text);
                    }
                    text += key.getKey();
                }
                break;

            // 부호 바꾸기(+/-)
            case SIGN:
                if (text.isEmpty()) {
                    text += "(-";
                    bracketCount++;
                } else {
                    if (text.endsWith("(-")) {
                        text = removeLastIndex(text, 2);
                        bracketCount--;
                    } else if (endsWithOpenBracket(text) || endsWithSigns(text)) {
                        text += "(-";
                        bracketCount++;
                    } else if (endsWithCloseBracket(text)) {
                        text += "*(-";
                        bracketCount++;
                    } else {
                        String[] splitText = text.split(SPLIT_SIGN_REGEX);
                        int splitTextSubtractIndex = splitText.length - 1;
                        double tempLastNumber = Double.parseDouble(splitText[splitTextSubtractIndex]);

                        if (tempLastNumber <= 0 && splitTextSubtractIndex > 0) {
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
                        StringBuilder textBuilder = new StringBuilder();
                        for (String string : splitText) {
                            textBuilder.append(string);
                        }
                        text = textBuilder.toString();
                    }
                }
                break;

            // 점(.)
            case DOT:
                if (text.isEmpty() || endsWithSigns(text) || endsWithOpenBracket(text)) {
                    text += "0" + key.getKey();
                } else if (endsWithCloseBracket(text)) {
                    text += "*0" + key.getKey();
                } else {
                    String[] splitText = text.split(SPLIT_SIGN_REGEX);
                    if (!splitText[splitText.length - 1].contains(key.getKey())) {
                        text += key.getKey();
                    }
                }
                break;

            // 결과(=)
            case RESULT:
                if (text.isEmpty()) {
                    break;
                }

                if (endsWithSigns(text)) {
                    text = removeLastIndex(text);
                }
                if (bracketCount > 0) {
                    for (; bracketCount > 0; bracketCount--) {
                        text += ")";
                    }
                }

                String[] splitText = text.split(SPLIT_SIGN_REGEX);
                // a-b일 때 '+' 기호를 중간에 넣는 작업
                // 예: 2-1 => 2, +, -1
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
                String result = new Calculator().calculate(splitText);
                text += key.getKey() + result; // 식=결과
                break;
            default:
                break;
        }
        return text;
    }

    private String removeLastIndex(String text) {
        return new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
    }

    private String removeLastIndex(String text, int count) {
        return new StringBuilder(text)
                .delete(text.length() - count, text.length()).toString();
    }
}
