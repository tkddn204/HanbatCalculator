package ssangwoo.com.hanbatcalculator;

import java.util.ArrayList;
import java.util.Stack;

import static ssangwoo.com.hanbatcalculator.Util.isNumeric;

/**
 * Created by ssangwoo on 2017-09-17.
 */
class Calculator {
    private Stack<String> signList;
    private ArrayList<String> postfix;
    private Stack<Double> numberList;

    String calculate(String[] splitText) {
        try {
            // Infix 표현식을 Postfix 표현식으로 바꿉니다.
            changeInfixToPostfix(splitText);

            // Postfix 로 변환된 식을 계산합니다.
            if (postfix.size() > 2) {
                calculatePostfix();
                // 계산한 후 출력 String 형태를 정리합니다.
                return arrangeExpression();
            } else {
                // 부호가 없는 식이면 그대로 출력합니다.
                return postfix.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "올바른 식이 아닙니다!";
        } finally {
            if (!signList.isEmpty()) {
                signList = new Stack<>();
            }
            if (!postfix.isEmpty()) {
                postfix = new ArrayList<>();
            }
            if (!numberList.isEmpty()) {
                numberList = new Stack<>();
            }
        }
    }

    private void changeInfixToPostfix(String[] splitText) {
        for (String string : splitText) {
            if (isNumeric(string)) {
                postfix.add(string);
            } else if (string.equals(")")) {
                while (!signList.isEmpty() && !signList.peek().equals("(")) {
                    postfix.add(signList.pop());
                }
                if (!signList.isEmpty()) {
                    signList.pop();
                }
            } else if (string.equals("(")) {
                signList.push(string);
            } else {
                if (!signList.isEmpty() && !isLowerPriority(string, signList.peek())) {
                    signList.push(string);
                } else {
                    while (!signList.isEmpty() && isLowerPriority(string, signList.peek())) {
                        if (!signList.peek().equals("(")) {
                            postfix.add(signList.pop());
                        } else {
                            break;
                        }
                    }
                    signList.push(string);
                }
            }
        }
        while (!signList.isEmpty()) {
            postfix.add(signList.pop());
        }
    }

    private void calculatePostfix() {
        double number1, number2;
        for (String string : postfix) {
            if (isNumeric(string)) {
                numberList.push(Double.parseDouble(string));
            } else {
                if (numberList.size() > 1) {
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
    }

    private String arrangeExpression() {
        if (!numberList.isEmpty()) {
            Double result = numberList.pop();
            if (result == 0.) {
                return "0";
            } else if (result.longValue() >= Long.MAX_VALUE ||
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
    }

    // Postfix 변경 시 우선순위 비교 메소드
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

    Calculator() {
        signList = new Stack<>();
        postfix = new ArrayList<>();
        numberList = new Stack<>();
    }
}
