package ru.puchkova.gradwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

    private final static String EMPTY = "";
    private final static boolean DEFAULT = false;
    private final static String ERROR_PASS = "Неверный пароль";
    private final static String ERROR_LOG = "Несуществующий логин";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();
    }

    private void init(){
        log = findViewById(R.id.log);
        pass = findViewById(R.id.pass);
        ImageButton accept = findViewById(R.id.accept);
        newbie = findViewById(R.id.newbie);

        newbie.setOnCheckedChangeListener(newbieOnCheckedChangeListener);

        ImageButton clear = findViewById(R.id.clear);
        clear.setOnClickListener(clearOnclickListener);
        accept.setOnClickListener(acceptOnClickListener);
    }

    View.OnClickListener clearOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            log.setText(EMPTY);
            pass.setText(EMPTY);
            newbie.setChecked(DEFAULT);
        }
    };

    View.OnClickListener acceptOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            App app = (App)StartActivity.this.getApplication();
            String login = log.getText().toString();
            String password = pass.getText().toString();
            boolean isCheck = newbie.isChecked();
            int isError = app.getId(login, password, isCheck);
            login(isError);
        }
    };

    private void login(int isError){
        if(isError == -1){
            Toast toast = Toast.makeText(getApplicationContext(),
                    ERROR_PASS, Toast.LENGTH_SHORT);
            toast.show();
        } else if(isError == -2) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    ERROR_LOG, Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    CompoundButton.OnCheckedChangeListener newbieOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences check = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = check.edit();
            editor.putBoolean("checked", isChecked);
        }
    };
}