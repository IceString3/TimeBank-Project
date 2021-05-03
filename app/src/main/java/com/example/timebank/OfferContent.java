package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OfferContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_content);
        boolean isOneTime = getIntent().getExtras().getBoolean("o_oneTime");

        String username = getIntent().getExtras().getString("o_username");
        String title = getIntent().getExtras().getString("o_title");
        String desc = getIntent().getExtras().getString("o_description");

        TextView textViewName = findViewById(R.id.textViewRealname);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        TextView textViewAvail = findViewById(R.id.textViewAvail);

        textViewName.setText(username);
        textViewTitle.setText(title);
        textViewDesc.setText(desc);
        if (isOneTime) {
            textViewAvail.setText("Una vez");
        } else {
            textViewAvail.setText("Siempre");
        }
//        getIntent().getExtras().remove("o_username");
//        getIntent().getExtras().remove("o_title");
//        getIntent().getExtras().remove("o_description");
//        getIntent().getExtras().remove("o_oneTime");
    }

    public void contactUser(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        String username = getIntent().getExtras().getString("o_username");
        intent.putExtra("o_username", username);
        startActivity(intent);
    }

    public void exchange(View v) {
        Intent intent = new Intent(getApplicationContext(), TimeExchangeOffer.class);
        intent.putExtra("o_id", getIntent().getExtras().getString("o_id"));
        intent.putExtra("o_username", getIntent().getExtras().getString("o_username"));
        intent.putExtra("o_title", getIntent().getExtras().getString("o_title"));
        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(getApplicationContext(), OfferListActivity.class);
        startActivity(intent);
    }
}