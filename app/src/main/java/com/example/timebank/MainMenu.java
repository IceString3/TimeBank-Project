package com.example.timebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class MainMenu extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Bienvenido/a, " + ParseUser.getCurrentUser().get("Name"));
    }

    public void offerCreate(View v) {
        Intent intent = new Intent(MainMenu.this, OfferCreate.class);
        startActivity(intent);
    }

    public void seeOffers(View v) {
        Intent intent = new Intent(MainMenu.this, OfferListActivity.class);
        startActivity(intent);
    }

    public void requestCreate(View v) {
        Intent intent = new Intent(MainMenu.this, RequestCreate.class);
        startActivity(intent);
    }

    public void seeRequests(View v) {
        Intent intent = new Intent(MainMenu.this, RequestListActivity.class);
        startActivity(intent);
    }


    public void search(View v) {
        Intent intent = new Intent(MainMenu.this, UserListActivity.class);
        startActivity(intent);
    }

    public void changeCommunity(View v) {
        Intent intent = new Intent(MainMenu.this, Community.class);
        startActivity(intent);
    }

    public void settings(View v) {
        Intent intent = new Intent(MainMenu.this, UserSettings.class);
        startActivity(intent);
    }

    public void logout(View v) {
        Intent intent = new Intent(MainMenu.this, MainActivity.class);
        ParseUser.logOut();
        startActivity(intent);
    }
}
