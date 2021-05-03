package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserEval extends AppCompatActivity {

    RatingBar score;
    EditText desc;
    String reviewedUser;
    float score1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_eval);

        score = findViewById(R.id.ratingBar1);
        desc = findViewById(R.id.editTextEvalDesc);
    }

    public void accept(View v) {
        final String offerTitle = getIntent().getExtras().getString("o_title");
        score = findViewById(R.id.ratingBar1);
        desc = findViewById(R.id.editTextEvalDesc);
        reviewedUser = getIntent().getExtras().getString("username");
        score.setStepSize(0.5f);
        score1 = score.getRating();

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
                    if (score1 >= 0 && score1 <= 5) {
                        String description = desc.getText().toString();

                        ParseObject review = new ParseObject("Reviews");
                        review.put("offer_title", offerTitle);
                        review.put("reviewed_user", reviewedUser);
                        review.put("reviewed_by", ParseUser.getCurrentUser().getUsername());
                        review.put("description", description);
                        review.put("score", score1);
                        review.saveInBackground();
                        Toast.makeText(getApplicationContext(), "Reseña enviada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "La puntuación debe estar entre 1 y 5.", Toast.LENGTH_SHORT).show();
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