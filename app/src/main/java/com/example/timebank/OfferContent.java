package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OfferContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_content);
        boolean isOneTime = getIntent().getExtras().getBoolean("o_oneTime");

        String offerID = getIntent().getExtras().getString("o_id");
        String username = getIntent().getExtras().getString("o_username");
        String title = getIntent().getExtras().getString("o_title");
        String desc = getIntent().getExtras().getString("o_description");

        TextView textViewName = findViewById(R.id.textViewRealname);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        TextView textViewAvail = findViewById(R.id.textViewAvail);

        Button exchange = findViewById(R.id.btnExchange);
        Button contact = findViewById(R.id.btnContact);

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
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
            query.getInBackground(offerID, new GetCallback<ParseObject>() {
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
//        getIntent().getExtras().remove("o_username");
//        getIntent().getExtras().remove("o_title");
//        getIntent().getExtras().remove("o_description");
//        getIntent().getExtras().remove("o_oneTime");
    }

    public void contactUser(View v) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        String username = getIntent().getExtras().getString("o_username");
        if (username.equals(ParseUser.getCurrentUser().getUsername())) {
            Toast.makeText(getApplicationContext(), "No puedes contactar contigo mismo", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("o_username", username);
            startActivity(intent);
        }
    }

    public void exchange(View v) {
        Intent intent = new Intent(getApplicationContext(), TimeExchangeOffer.class);
        String username = getIntent().getExtras().getString("o_username");
        if (username.equals(ParseUser.getCurrentUser().getUsername())) {
            Toast.makeText(getApplicationContext(), "No puedes intercambiar tiempo contigo mismo", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("o_id", getIntent().getExtras().getString("o_id"));
            intent.putExtra("o_username", getIntent().getExtras().getString("o_username"));
            intent.putExtra("o_title", getIntent().getExtras().getString("o_title"));
            startActivity(intent);
        }
    }

    public void cancel(View v) {
        Intent intent = new Intent(getApplicationContext(), OfferListActivity.class);
        startActivity(intent);
    }
}