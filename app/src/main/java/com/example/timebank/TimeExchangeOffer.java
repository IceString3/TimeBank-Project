package com.example.timebank;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeExchangeOffer extends Activity {
    ParseUser user = new ParseUser();
    String username;
    EditText editTextPay;
    String offerID;
    boolean isOneTime;
    int hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_exchange_offer);

        TextView offerTitle = findViewById(R.id.textViewOfferTitle2);
        TextView userOffer = findViewById(R.id.textViewUserOffer);
        offerTitle.setText(getIntent().getExtras().getString("o_title"));
        userOffer.setText(getIntent().getExtras().getString("o_username"));

    }

    public void accept(View v) {
        editTextPay = findViewById(R.id.editTextPay);
        username = getIntent().getExtras().getString("o_username");
        offerID = getIntent().getExtras().getString("o_id");
        isOneTime = getIntent().getExtras().getBoolean("o_oneTime");
        hours = Integer.parseInt(editTextPay.getText().toString());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Credits");
        // query.whereEqualTo("username", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject credits : objects) {
                            try {
                                if (credits.getParseUser("username").fetchIfNeeded().getUsername().equals(username)) {
                                    credits.increment("time_credits", hours);
                                    credits.saveInBackground();
                                    break;
                                }
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    }


                    if (isOneTime) {
                        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Offer");
                        query3.getInBackground(offerID, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    object.put("completed_at", LocalDateTime.now());
                                    object.saveInBackground();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Credits");
                    final String currentUser = ParseUser.getCurrentUser().getUsername();
                    query2.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    for (ParseObject credits : objects) {
                                        try {
                                            if (credits.getParseUser("username").fetchIfNeeded().getUsername().equals(currentUser)) {
                                                credits.increment("time_credits", -1 * hours);
                                                credits.saveInBackground();
                                                break;
                                            }
                                        } catch (ParseException parseException) {
                                            parseException.printStackTrace();
                                        }
                                    }
                                }

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido pagar al usuario", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea hacer una reseña del usuario?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), UserEval.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), ContentMain.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}