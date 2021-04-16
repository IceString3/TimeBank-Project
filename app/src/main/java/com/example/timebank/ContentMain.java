package com.example.timebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class ContentMain extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        String name = "";



        Button createOffer = findViewById(R.id.offerCreateButton);
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Bienvenido/a, " + ParseUser.getCurrentUser().get("Name"));
    }

    public void offerCreate(View v) {
        Intent intent = new Intent(ContentMain.this, OfferCreate.class);
        startActivity(intent);
    }

    public void seeOffers(View v) {
        Intent intent = new Intent(ContentMain.this, OfferListActivity.class);
        startActivity(intent);
    }

    public void requestCreate(View v) {
        Intent intent = new Intent(ContentMain.this, RequestCreate.class);
        startActivity(intent);
    }

    public void seeRequests(View v) {
        Intent intent = new Intent(ContentMain.this, RequestListActivity.class);
        startActivity(intent);
    }


    public void search(View v) {
        Intent intent = new Intent(ContentMain.this, UserListActivity.class);
        startActivity(intent);
    }

    public void changeCommunity(View v) {
        Intent intent = new Intent(ContentMain.this, Community.class);
        startActivity(intent);
    }

    public void logout(View v) {
        Intent intent = new Intent(ContentMain.this, MainActivity.class);
        ParseUser.logOut();
        startActivity(intent);
    }
}
