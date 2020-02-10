package ru.puchkova.gradwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private EditText log;
    private EditText pass;
    private CheckBox newbie;

    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;

    private boolean showFlag = true;

    private ImageButton show;

    private final static String EMPTY = "";
    private final static boolean DEFAULT = false;
    private final static String HIDE = "●";

    private String ERROR_PASS;
    private String ERROR_LOG;
    private String ERROR_LONG;
    private String ERROR_SHORT;
    private String ERROR_EMPTY;

    private String password = EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ERROR_PASS = getString(R.string.error_pass);
        ERROR_LOG = getString(R.string.error_log);
        ERROR_LONG = getString(R.string.error_long);
        ERROR_SHORT = getString(R.string.error_short);
        ERROR_EMPTY = getString(R.string.error_empty);

        init();
    }

    private void init(){
        log = findViewById(R.id.log);
        pass = findViewById(R.id.pass);
        ImageButton accept = findViewById(R.id.accept);
        newbie = findViewById(R.id.newbie);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);

        show = findViewById(R.id.show);

        newbie.setOnCheckedChangeListener(newbieOnCheckedChangeListener);

        ImageButton clear = findViewById(R.id.clear);
        clear.setOnClickListener(clearOnclickListener);
        accept.setOnClickListener(acceptOnClickListener);
        log.setOnFocusChangeListener(logOnFocusChangeListener);
        pass.setOnFocusChangeListener(passOnFocusChangeListener);
        pass.setCursorVisible(false);
        show.setOnClickListener(showOnClickListener);

        one.setOnClickListener(numbersOnClickListener);
        two.setOnClickListener(numbersOnClickListener);
        three.setOnClickListener(numbersOnClickListener);
        four.setOnClickListener(numbersOnClickListener);
        five.setOnClickListener(numbersOnClickListener);
        six.setOnClickListener(numbersOnClickListener);
        seven.setOnClickListener(numbersOnClickListener);
        eight.setOnClickListener(numbersOnClickListener);
        nine.setOnClickListener(numbersOnClickListener);
        zero.setOnClickListener(numbersOnClickListener);


    }

    View.OnClickListener clearOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            log.setText(EMPTY);
            pass.setText(EMPTY);
            newbie.setChecked(DEFAULT);
            password = EMPTY;
        }
    };

    View.OnClickListener acceptOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            App app = (App)StartActivity.this.getApplication();
            String login = log.getText().toString();
            boolean isCheck = newbie.isChecked();
            int isError;
            if(password.equals(EMPTY) || login.equals(EMPTY)){
                isError = -5;
            } else if (password.length() > 4){
                isError = -4;
            } else if (password.length() < 4){
                isError = -3;
            } else {
                isError = app.getId(login, password, isCheck);
            }
            login(isError);
        }
    };

    private void login(int isError){
        Toast toast;
        switch (isError){
            case -1:
                toast = Toast.makeText(getApplicationContext(),
                        ERROR_PASS, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case -2:
                toast = Toast.makeText(getApplicationContext(),
                        ERROR_LOG, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case -3:
                toast = Toast.makeText(getApplicationContext(),
                        ERROR_SHORT, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case -4:
                toast = Toast.makeText(getApplicationContext(),
                        ERROR_LONG, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case -5:
                toast = Toast.makeText(getApplicationContext(),
                        ERROR_EMPTY, Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
    }

    View.OnClickListener numbersOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            password = password + ((Button) v).getText().toString();
            if (showFlag) {
                pass.append(HIDE);
            } else {
                pass.append(((Button) v).getText());
            }
            if(pass.getText().toString().length() > 3){
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                zero.setVisibility(View.GONE);
            }
        }
    };

    CompoundButton.OnCheckedChangeListener newbieOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences check = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = check.edit();
            editor.putBoolean("checked", isChecked);
        }
    };

    View.OnFocusChangeListener passOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            pass.setShowSoftInputOnFocus(false);
            if (v.isFocused() && ((EditText) v).getText().toString().length() < 4){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
                four.setVisibility(View.VISIBLE);
                five.setVisibility(View.VISIBLE);
                six.setVisibility(View.VISIBLE);
                seven.setVisibility(View.VISIBLE);
                eight.setVisibility(View.VISIBLE);
                nine.setVisibility(View.VISIBLE);
                zero.setVisibility(View.VISIBLE);
            }
            else {
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                zero.setVisibility(View.GONE);
            }
        }
    };

    View.OnFocusChangeListener logOnFocusChangeListener =  new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(log.getWindowToken(), 0);
            }
        }
    };

    View.OnClickListener showOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(showFlag){
                show.setBackgroundResource(R.drawable.eye_close_opacity);
                pass.setText(password);
                showFlag = false;
            } else {
                show.setBackgroundResource(R.drawable.eye_open_opacity);
                pass.setText(EMPTY);
                if (password != null) {
                    for (int count = 0; count < password.length(); count++) {
                        pass.append(HIDE);
                    }
                }
                showFlag = true;
            }
        }
    };
}