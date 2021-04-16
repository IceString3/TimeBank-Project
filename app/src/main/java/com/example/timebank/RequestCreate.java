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

        ParseObject request = new ParseObject("Request");
        long expiryDate = getIntent().getExtras().getLong("task_date");
        Date date = new Date();
        date.setTime(expiryDate);
        request.put("expires_at", date);
        request.put("username", ParseUser.getCurrentUser());
        request.put("request_title", requestTitle.getText().toString());
        request.put("request_desc", requestDesc.getText().toString());
        request.put("one_time_only", oneTime.isChecked());
        request.put("is_finished", false);

        request.saveInBackground();
        Intent intent = new Intent(RequestCreate.this, ContentMain.class);
        sharedpreferences = getSharedPreferences("createRequestTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(RequestCreate.this, ContentMain.class);
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
        startActivity(intent);
    }
}