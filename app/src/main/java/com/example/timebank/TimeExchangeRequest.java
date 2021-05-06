package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

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

import java.time.LocalDateTime;
import java.util.List;

public class TimeExchangeRequest extends AppCompatActivity {
    ParseUser user = new ParseUser();
    AlertDialog.Builder builder;
    String username;
    EditText editTextCharge;
    String requestID;
    boolean isOneTime;
    int hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_exchange_request);

        TextView requestTitle = findViewById(R.id.textViewRequestTitle2);
        TextView userRequest = findViewById(R.id.textViewUserRequest);
        requestTitle.setText(getIntent().getExtras().getString("r_title"));
        userRequest.setText(getIntent().getExtras().getString("r_username"));

    }

    public void accept(View v) {
        final String requestTitleText = getIntent().getExtras().getString("r_title");
        editTextCharge = findViewById(R.id.editTextCharge);
        username = getIntent().getExtras().getString("r_username");
        requestID = getIntent().getExtras().getString("r_id");
        isOneTime = getIntent().getExtras().getBoolean("r_oneTime");
        hours = Integer.parseInt(editTextCharge.getText().toString());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Credits");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject credits : objects) {
                            try {
                                if (credits.getParseUser("username").fetchIfNeeded().getUsername().equals(username)) {
                                    credits.increment("time_credits", -1 * hours);
                                    credits.saveInBackground();
                                    user.increment("total_requests");
                                    user.saveInBackground();
                                    break;
                                }
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    }


                    if (isOneTime) {
                        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Request");
                        query3.getInBackground(requestID, new GetCallback<ParseObject>() {
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
                                                credits.increment("time_credits", hours);
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
                    Toast.makeText(getApplicationContext(), "No se ha podido cobrar al usuario", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea hacer una reseña del usuario?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), UserEval.class);
                intent.putExtra("username", username);
                intent.putExtra("r_title", requestTitleText);
                intent.putExtra("r_id", requestID);
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