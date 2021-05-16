package com.example.timebank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }

        Button newAccount = findViewById(R.id.buttonNewAcc);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_account.class);
                startActivity(intent);
            }
        });

        Button login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userText = findViewById(R.id.usernameTextView);
                EditText passText = findViewById(R.id.passwordTextView);
                ParseUser.logInInBackground(userText.getText().toString(), passText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            intent.putExtra("Username", userText.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//        Button forgotPassword = findViewById (R.id.buttonForgotPwd);
//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Reset_Password.class);
//                startActivity(intent);
//            }
//        });


        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.user_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId ();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected (item);
//    }
}