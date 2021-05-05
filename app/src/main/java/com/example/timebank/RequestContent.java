package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RequestContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_content);
        boolean isOneTime = getIntent().getExtras().getBoolean("r_oneTime");

        String username = getIntent().getExtras().getString("r_username");

        TextView textViewName = findViewById(R.id.textViewRealname);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        TextView textViewAvail = findViewById(R.id.textViewAvail);

        textViewName.setText(username);
        textViewTitle.setText(getIntent().getExtras().getString("r_title"));
        textViewDesc.setText(getIntent().getExtras().getString("r_description"));
        if (isOneTime) {
            textViewAvail.setText("Una vez");
        } else {
            textViewAvail.setText("Siempre");
        }
    }

    public void contactUser(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        String username = getIntent().getExtras().getString("r_username");
        intent.putExtra("r_username", username);
        startActivity(intent);
    }

    public void exchange(View v) {
        Intent intent = new Intent(getApplicationContext(), TimeExchangeRequest.class);
        intent.putExtra("r_id", getIntent().getExtras().getString("r_id"));
        intent.putExtra("r_username", getIntent().getExtras().getString("r_username"));
        intent.putExtra("r_title", getIntent().getExtras().getString("r_title"));
        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(getApplicationContext(), RequestListActivity.class);
        startActivity(intent);
    }
}