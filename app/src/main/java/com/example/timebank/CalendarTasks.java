package com.example.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarTasks extends AppCompatActivity {

    Calendar calendar;
    CalendarView calendarView;

    Date selectedDate;
    Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tasks);

        setTitle("Selección de fecha");

        calendar = Calendar.getInstance();
        today = calendar.getTime();

        calendarView = findViewById(R.id.calendarView);


        Button returnToTodayBtn = findViewById(R.id.btnReturnToToday);
        returnToTodayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar.setTime(today);
                calendarView.setDate(calendar.getTimeInMillis(), false, false);
            }
        });


        Button acceptBtn = findViewById(R.id.btnAccept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestCreate.class);
                intent.putExtra("task_date", selectedDate.getTime());
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = "Selected date Day: " + i2 + " Month: " + (i1 + 1) + " Year: " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                calendar.set(i, i1, i2, 0, 0, 0);
                calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
                selectedDate = calendar.getTime();
                Log.i("selected_date", String.valueOf(selectedDate));
            }
        });
    }
}