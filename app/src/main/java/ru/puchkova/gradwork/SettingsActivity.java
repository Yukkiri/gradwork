package ru.puchkova.gradwork;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private EditText lastPass;
    private EditText newPass;
    private Spinner language;
    private Spinner theme;
    private ImageButton accept;
    private ImageButton cancel;

    private final String OLD_PASS = getString(R.string.old_pass_empty);
    private final String NEW_PASS = getString(R.string.new_pass_empty);

    private static final String EMPTY = "";

    private static final int EMPTY_PASSES = 0;
    private static final int EMPTY_LAST = -1;
    private static final int EMPTY_NEW = -2;
    private static final int NOT_EMPTY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    public void init(){
        lastPass = findViewById(R.id.lastPass);
        newPass = findViewById(R.id.newPass);
        language = findViewById(R.id.language);
        theme = findViewById(R.id.theme);
        accept = findViewById(R.id.accept);
        cancel = findViewById(R.id.cancel);

        setOnClick();
        initSpinnerLanguage();
        initSpinnerTheme();
    }

    public void setOnClick(){
        cancel.setOnClickListener(cancelOnclickListener);
        accept.setOnClickListener(acceptOnClickListener);
    }

    private void initSpinnerLanguage() {
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapterLanguage);
    }

    private void initSpinnerTheme() {
        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(this, R.array.theme, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theme.setAdapter(adapterColor);
    }

    View.OnClickListener cancelOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener acceptOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int passAnswer = isPassNotEmpty();
            if (passAnswer == EMPTY_LAST){
                Toast toast = Toast.makeText(getApplicationContext(),
                        OLD_PASS, Toast.LENGTH_SHORT);
                toast.show();
            } else if(passAnswer == EMPTY_NEW){
                Toast toast = Toast.makeText(getApplicationContext(),
                        NEW_PASS, Toast.LENGTH_SHORT);
                toast.show();
            } else if(passAnswer == NOT_EMPTY){

                //тут
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Два пароля введены", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "пусто", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };


    //???????????????????
    public void changeLanguage(){
        Locale localeRu = new Locale("ru");
        Locale localeEn = new Locale("en");
        Configuration config = new Configuration();
        String languageString = language.getSelectedItem().toString();
        if (languageString.equals("English")){
            config.setLocale(localeEn);
        } else {
            config.setLocale(localeRu);
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public int isPassNotEmpty(){
        if(lastPass.getText().toString().equals(EMPTY) && newPass.getText().toString().equals(EMPTY))
            return EMPTY_PASSES;
        else if(lastPass.getText().toString().equals(EMPTY))
            return EMPTY_LAST;
        else if(newPass.getText().toString().equals(EMPTY))
            return EMPTY_NEW;
        else
            return NOT_EMPTY;
    }

    /*
    @IntDef({Color.YELLOW, Color.BLACK, Color.LILAC, Color.CYAN, Color.BEIGE})
    private @interface Color {
        int YELLOW = 0;
        int BLACK = 1;
        int LILAC = 2;
        int CYAN = 3;
        int BEIGE = 4;
    }
     */

    private void setColor(int color){
        switch (color){

        }
    }
}