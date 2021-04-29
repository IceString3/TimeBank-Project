package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserEval extends AppCompatActivity {

    EditText score;
    EditText desc;
    String reviewedUser;
    int score1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);

        score = findViewById(R.id.editTextScore);
        desc = findViewById(R.id.editTextEvalDesc);
    }

    public void accept(View v) {
        score = findViewById(R.id.editTextScore);
        desc = findViewById(R.id.editTextEvalDesc);
        reviewedUser = getIntent().getExtras().getString("username");
        score1 = Integer.parseInt(score.getText().toString());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("reviewed_by", ParseUser.getCurrentUser().getUsername());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Ya has hecho una reseña sobre ese usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (score1 >= 0 && score1 <= 10) {
                        String description = desc.getText().toString();

                        ParseObject review = new ParseObject("Reviews");
                        review.put("reviewed_user", reviewedUser);
                        review.put("reviewed_by", ParseUser.getCurrentUser().getUsername());
                        review.put("description", description);
                        review.put("score", score1);
                        review.saveInBackground();
                        Toast.makeText(getApplicationContext(), "Reseña enviada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "La puntuación debe estar entre 0 y 10.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void cancel(View v) {
        Intent intent = new Intent(getApplicationContext(), ContentMain.class);
        startActivity(intent);
    }
}