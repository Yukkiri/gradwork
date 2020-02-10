package ru.puchkova.gradwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
    private Button ok;

    private int noteId;
    private boolean checked;

    private long endDate = -1L;
    private static final int newNote = -1;

    private static final String EMPTY = "";

    private static final String CHOOSE_DATE = "Выберите дату дедлайна";
    private static final String CHOOSE_TIME = "Выберите время дедлайна";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();

        noteId = getNote();

        setOCLs(noteId);
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
        ok = findViewById(R.id.ok);

        ok.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        time.setVisibility(View.GONE);
    }

    public int getNote(){
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", newNote);
        String titleText = intent.getStringExtra("title");
        String descriptionText = intent.getStringExtra("description");
        String dateOfDeadline = intent.getStringExtra("deadline");
        checked = intent.getBooleanExtra("isChecked", false);


        title.setText(titleText);
        noteText.setText(descriptionText);

        if(dateOfDeadline != null) {
            long time = Long.parseLong(dateOfDeadline);

            if(time != endDate) {
                LocalDate deadlineDateSaved = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalTime deadlineTimeSaved = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime();

                deadlineDate.setText(deadlineDateSaved.toString());
                deadlineTime.setText(deadlineTimeSaved.toString());
                deadlineDate.setVisibility(View.VISIBLE);
                deadlineTime.setVisibility(View.VISIBLE);
                deadlineTitle.setVisibility(View.VISIBLE);
            }
        }

        return noteId;
    }

    public void setOCLs(int noteId){
        constraintLayout.setOnClickListener(layoutOnClickListener);
        deadline.setOnClickListener(deadlineOnClickListener);
        date.setOnDateChangeListener(calendarOnDateChangeListener);
        time.setOnTimeChangedListener(timeOnTimeChangeListener);

        title.setOnKeyListener(enterOnKeyListener);
        ok.setOnClickListener(okOnClickListener);

        accept.setOnClickListener(acceptOnClickListener);
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
            if(noteId == newNote){
                save();
            } else {
                edit();
            }
        }
    };

    View.OnClickListener acceptOnClickListenerEdit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            App app = (App)NoteActivity.this.getApplication();
            String noteTitle = title.getText().toString();
            String noteBody = noteText.getText().toString();
            app.changeNote(new Notes(noteId, noteTitle, noteBody, Long.toString(endDate), checked));
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
            view.setVisibility(View.GONE);
            time.setVisibility(View.VISIBLE);
            ok.setVisibility(View.VISIBLE);
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
        }
    };

    View.OnClickListener okOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ok.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noteId == newNote){
            save();
        } else {
            edit();
        }
    }

    public void save(){
        App app = (App)NoteActivity.this.getApplication();
        String noteTitle = title.getText().toString();
        String noteBody = noteText.getText().toString();
        app.createNote(noteTitle, noteBody, endDate);
        finish();
    }

    public void edit(){
        App app = (App)NoteActivity.this.getApplication();
        String noteTitle = title.getText().toString();
        String noteBody = noteText.getText().toString();
        app.changeNote(new Notes(noteId, noteTitle, noteBody, Long.toString(endDate), checked));
        finish();
    }
}
