package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class new_account2 extends AppCompatActivity {

    TextView textreceived;
    Button nextScr;

    EditText emailText;
    EditText userText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account2);

        Button signUp = findViewById(R.id.btnNext);

        final String name = getIntent().getExtras().getString("Name");
        final String surname1 = getIntent().getExtras().getString("Surname1");
        final String surname2 = getIntent().getExtras().getString("Surname2");
        final String loc = getIntent().getExtras().getString("Location");
        final String profession = getIntent().getExtras().getString("Profession");
        final String services = getIntent().getExtras().getString("Services");



    }

    public void signUp(View v) {
        emailText = findViewById(R.id.editTextEmail);
        userText = findViewById(R.id.editTextUsername);
        passwordText = findViewById(R.id.editTextPass);

        if (userText.getText().toString().equals("") || passwordText.getText().toString().equals("") || emailText.getText().toString().equals("")) {
            Toast.makeText(new_account2.this, "A username, password and email are required.", Toast.LENGTH_SHORT).show();
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(userText.getText().toString());
            user.setPassword(passwordText.getText().toString());
            user.setEmail(emailText.getText().toString());
            user.put("Name", getIntent().getExtras().getString("Name"));
            user.put("Surname1", getIntent().getExtras().getString("Surname1"));
            user.put("Surname2", getIntent().getExtras().getString("Surname2"));
            user.put("Location", getIntent().getExtras().getString("Location"));
            user.put("Profession", getIntent().getExtras().getString("Profession"));
            user.put("Services", getIntent().getExtras().getString("Services"));
            user.put("total_spent_hours", 0);
            user.put("total_offers", 0);
            user.put("total_requests" , 0);


            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(new_account2.this,
                                Community.class);
                        ParseObject credits = new ParseObject("Credits");
                        credits.put("username", ParseUser.getCurrentUser());
                        credits.put("time_credits", 5);
                        credits.saveInBackground();
                        intent.putExtra("Username", userText.getText().toString());
                        startActivity(intent);
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}