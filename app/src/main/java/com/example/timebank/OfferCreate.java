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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class OfferCreate extends AppCompatActivity {

    EditText offerTitle;
    EditText offerDesc;
    CheckBox oneTime;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_create);
        setTitle("Crear tarea ofertada");

        offerTitle = findViewById(R.id.editTextOfferTitle);
        offerDesc = findViewById(R.id.editTextOfferDesc);
        oneTime = findViewById(R.id.oneTimeCheckBox);

        sharedpreferences = getSharedPreferences("createOfferTemp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("offerTitle")) {
            offerTitle.setText(sharedpreferences.getString("offerTitle", ""));
        }
        if (sharedpreferences.contains("offerDesc")) {
            offerDesc.setText(sharedpreferences.getString("offerDesc", ""));
        }
        if (sharedpreferences.contains("offerOneTime")) {
            oneTime.setChecked(sharedpreferences.getBoolean("offerOneTime", false));
        }
    }

    public void saveChanges(View v) {

        ParseObject offer = new ParseObject("Offer");
        try {
            long expiryDate = getIntent().getExtras().getLong("task_date");
            Date date = new Date();
            date.setTime(expiryDate);
            offer.put("expires_at", date);
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

        offer.put("expires_at", date2);
        offer.put("username", ParseUser.getCurrentUser());
        offer.put("offer_title", offerTitle.getText().toString());
        offer.put("offer_desc", offerDesc.getText().toString());
        offer.put("one_time_only", oneTime.isChecked());
        offer.put("is_finished", false);

        offer.saveInBackground();
        Intent intent = new Intent(OfferCreate.this, ContentMain.class);
        sharedpreferences = getSharedPreferences("createOfferTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(OfferCreate.this, ContentMain.class);
        startActivity(intent);
    }

    public void calendar(View v) {
        Intent intent = new Intent(OfferCreate.this, CalendarTasks.class);
        sharedpreferences = getSharedPreferences("createOfferTemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("offerTitle", offerTitle.getText().toString());
        editor.putString("offerDesc", offerDesc.getText().toString());
        editor.putBoolean("offerOneTime", oneTime.isChecked());
        editor.apply();
        startActivity(intent);
    }
}