package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class UserEval extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);
    }

    public void accept() {
        EditText score = findViewById(R.id.editTextScore);
        EditText desc = findViewById(R.id.editTextEvalDesc);
        if (Integer.parseInt(score.getText().toString()) >= 0 && Integer.parseInt(score.getText().toString()) <= 10) {
            int score1 = Integer.parseInt(score.getText().toString());
            String description = desc.getText().toString();

            ParseObject review = new ParseObject("Reviews");
            review.put("reviewed_user", getIntent().getExtras().getString("username"));
            review.put("reviewed_by", ParseUser.getCurrentUser().getUsername());
            review.put("score", score1);
        } else {
            Toast.makeText(getApplicationContext(), "La puntuación debe estar entre 0 y 10.", Toast.LENGTH_SHORT).show();
        }
    }
}