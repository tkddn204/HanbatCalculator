package ssangwoo.com.hanbatcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static ssangwoo.com.hanbatcalculator.Util.*;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private Button[] btnKeypad;

    private ControlText controlText;
    private String bufferText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initLayout();
    }

    private void init() {
        btnKeypad = new Button[Keypad.values().length];
        controlText = new ControlText();
        bufferText = "";
    }

    private void initLayout() {
        textResult = (TextView) findViewById(R.id.text_result);
        for (Keypad keypad : Keypad.values()) {
            makeKeypad(keypad);
        }
    }

    private void makeKeypad(final Keypad keypad) {
        btnKeypad[keypad.ordinal()] = (Button) findViewById(keypad.getId());
        btnKeypad[keypad.ordinal()].setText(String.valueOf(keypad.getKey()));

        View.OnClickListener keypadOnClickListener;
        if (isNumeric(keypad.getKey())) {
            keypadOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferText = controlText
                            .checkBufferAndPutNumber(bufferText, keypad);
                    textResult.setText(bufferText);
                }
            };
        } else if (keypad.equals(Keypad.RESULT)) {
            keypadOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferText = controlText
                            .checkBufferAndPutSign(bufferText, keypad);
                    textResult.setText(bufferText);
                    bufferText = "";
                }
            };
        } else {
            keypadOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferText = controlText
                            .checkBufferAndPutSign(bufferText, keypad);
                    textResult.setText(bufferText);
                }
            };
        }

        btnKeypad[keypad.ordinal()]
                .setOnClickListener(keypadOnClickListener);
    }
}
