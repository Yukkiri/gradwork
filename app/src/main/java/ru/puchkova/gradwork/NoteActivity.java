package ru.puchkova.gradwork;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.GregorianCalendar;

public class NoteActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private EditText title;
    private EditText noteText;
    private ImageButton deadline;
    private ImageButton accept;
    private CalendarView date;
    private TimePicker time;
    private TextView deadlineDate;
    private TextView deadlineTime;
    private TextView deadlineTitle;

    private long endDate = -1L;

    private static final String CHOOSE_DATE = "Выберите дату дедлайна";
    private static final String CHOOSE_TIME = "Выберите время дедлайна";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();
    }

    public void init(){
        constraintLayout = findViewById(R.id.constraintLayout);
        title = findViewById(R.id.title);
        noteText = findViewById(R.id.note_text);
        deadline = findViewById(R.id.deadline);
        accept = findViewById(R.id.save);
        date = findViewById(R.id.calendar_date);
        time = findViewById(R.id.time_deadline);
        deadlineDate = findViewById(R.id.date);
        deadlineTime = findViewById(R.id.time);
        deadlineTitle = findViewById(R.id.deadline_title);

        date.setVisibility(View.GONE);
        time.setVisibility(View.GONE);

        setOCLs();
    }

    public void setOCLs(){
        constraintLayout.setOnClickListener(layoutOnClickListener);
        deadline.setOnClickListener(deadlineOnClickListener);
        accept.setOnClickListener(acceptOnClickListener);
        date.setOnDateChangeListener(calendarOnDateChangeListener);
        time.setOnTimeChangedListener(timeOnTimeChangeListener);

        title.setOnKeyListener(enterOnKeyListener);
    }

    View.OnClickListener layoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            noteText.requestFocus();
        }
    };

    View.OnClickListener acceptOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            App app = (App)NoteActivity.this.getApplication();
            String noteTitle = title.getText().toString();
            String noteBody = noteText.getText().toString();
            app.createNote(noteTitle, noteBody, endDate);
            finish();
        }
    };

    View.OnClickListener deadlineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    CHOOSE_DATE, Toast.LENGTH_SHORT);
            toast.show();
            date.setVisibility(View.VISIBLE);
        }
    };

    View.OnKeyListener enterOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                noteText.requestFocus();
                return true;
            }
            return false;
        }
    };

    CalendarView.OnDateChangeListener calendarOnDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String endDateTxt = year + "-" + month + "-" + dayOfMonth;
            deadlineDate.setText(endDateTxt);
            deadlineDate.setVisibility(View.VISIBLE);
            deadlineTitle.setVisibility(View.VISIBLE);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(year, month, dayOfMonth);
            endDate = gregorianCalendar.getTimeInMillis();
            view.setVisibility(View.INVISIBLE);
            time.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(getApplicationContext(),
                    CHOOSE_TIME, Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    TimePicker.OnTimeChangedListener timeOnTimeChangeListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            String endTimeTxt = hourOfDay + ":" + minute;
            deadlineTime.setText(endTimeTxt);
            deadlineTime.setVisibility(View.VISIBLE);
            endDate = endDate + hourOfDay*360 + minute*60;
            time.setVisibility(View.INVISIBLE);
        }
    };
}
