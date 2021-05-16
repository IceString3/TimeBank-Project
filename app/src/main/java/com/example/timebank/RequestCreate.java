package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class RequestCreate extends AppCompatActivity {

    EditText requestTitle;
    EditText requestDesc;
    CheckBox oneTime;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);
        setTitle("Demandar un servicio");

        requestTitle = findViewById(R.id.editTextRequestTitle);
        requestDesc = findViewById(R.id.editTextRequestDesc);
        oneTime = findViewById(R.id.oneTimeCheckBoxR);

        sharedpreferences = getSharedPreferences("createRequestTemp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("requestTitle")) {
            requestTitle.setText(sharedpreferences.getString("requestTitle", ""));
        }
        if (sharedpreferences.contains("requestDesc")) {
            requestDesc.setText(sharedpreferences.getString("requestDesc", ""));
        }
        if (sharedpreferences.contains("requestOneTime")) {
            oneTime.setChecked(sharedpreferences.getBoolean("requestOneTime", false));
        }
    }

    public void saveChanges(View v) {

        String[] categoriesList = getIntent().getExtras().getStringArray("categories");

        ParseObject request = new ParseObject("Request");
        try {
            long expiryDate = getIntent().getExtras().getLong("task_date");
            Date date = new Date();
            date.setTime(expiryDate);
            request.put("expires_at", date);
        } catch (NullPointerException e) {
            System.out.println("Fecha no encontrada");
        }
        String myDate = "31/12/2099 23:59:59";  // 4102444799000L
        LocalDateTime localDateTime = LocalDateTime.parse(myDate,
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") );

        long millis = localDateTime
                .atZone(ZoneId.of("UTC"))
                .toInstant().toEpochMilli();
        Date date2 = new Date(millis);

        request.put("expires_at", date2);
        request.put("username", ParseUser.getCurrentUser());
        request.put("request_title", requestTitle.getText().toString());
        request.put("request_desc", requestDesc.getText().toString());
        request.put("one_time_only", oneTime.isChecked());
        request.put("community", ParseUser.getCurrentUser().getString("community_name"));
        request.put("times_completed", 0);
        request.put("is_finished", false);
        if (categoriesList != null) {
            request.put("categories", Arrays.asList(categoriesList));
        } else {
            request.put("categories", Arrays.asList("ninguna"));
        }

        request.saveInBackground();
        Intent intent = new Intent(RequestCreate.this, MainMenu.class);
        sharedpreferences = getSharedPreferences("createRequestTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(RequestCreate.this, MainMenu.class);
        startActivity(intent);
    }

    public void configCat(View v) {
        Intent intent = new Intent(RequestCreate.this, Categories.class);
        intent.putExtra("taskType", "request");
        sharedpreferences = getSharedPreferences("createRequestTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("requestTitle", requestTitle.getText().toString());
        editor.putString("requestDesc", requestTitle.getText().toString());
        editor.putBoolean("requestOneTime", oneTime.isChecked());
        editor.apply();
        startActivity(intent);
    }

    public void calendar(View v) {
        Intent intent = new Intent(RequestCreate.this, CalendarTasks.class);
        sharedpreferences = getSharedPreferences("createRequestTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("requestTitle", requestTitle.getText().toString());
        editor.putString("requestDesc", requestDesc.getText().toString());
        editor.putBoolean("requestOneTime", oneTime.isChecked());
        editor.apply();
        intent.putExtra("TaskCreated", "request");
        startActivity(intent);
    }
}