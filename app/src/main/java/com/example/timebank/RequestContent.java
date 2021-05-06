package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RequestContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_content);
        boolean isOneTime = getIntent().getExtras().getBoolean("r_oneTime");

        String requestID = getIntent().getExtras().getString("r_id");
        String username = getIntent().getExtras().getString("r_username");
        String title = getIntent().getExtras().getString("r_title");
        String desc = getIntent().getExtras().getString("r_description");

        TextView textViewName = findViewById(R.id.textViewRealname);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        TextView textViewAvail = findViewById(R.id.textViewAvail);

        Button exchange = findViewById(R.id.btnExchangeR);
        Button contact = findViewById(R.id.btnContactR);

        if (username.equals(ParseUser.getCurrentUser().getUsername())) {
            exchange.setVisibility(View.GONE);
            contact.setVisibility(View.GONE);
        }

        textViewName.setText(username);
        textViewTitle.setText(title);
        textViewDesc.setText(desc);

        if (isOneTime) {
            textViewAvail.setText("Una vez");
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
            query.getInBackground(requestID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String myDate = "31/12/2099 23:59:59";  // 4102444799000L
                        LocalDateTime localDateTime = LocalDateTime.parse(myDate,
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") );

                        long millis = localDateTime
                                .atZone(ZoneId.of("UTC"))
                                .toInstant().toEpochMilli();
                        Date date = new Date(millis);
                        try {
                            if (object.getDate("expires_at").equals(date)) {
                                textViewAvail.setText("Siempre");
                            } else {
                                textViewAvail.setText(object.getString("expires_at"));
                            }
                        } catch (NullPointerException exception){
                            System.out.println("Fecha de expiración vacía");
                            textViewAvail.setText("Siempre");
                        }
                    }
                }
            });
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