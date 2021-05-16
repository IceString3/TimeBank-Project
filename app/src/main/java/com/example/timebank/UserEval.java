package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class UserEval extends AppCompatActivity {

    AlertDialog.Builder builder;
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
        //final String offerTitle = getIntent().getExtras().getString("o_title");
        score = findViewById(R.id.ratingBar1);
        desc = findViewById(R.id.editTextEvalDesc);
        reviewedUser = getIntent().getExtras().getString("username");
        score.setStepSize(0.5f);
        score1 = score.getRating();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("reviewed_by", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("reviewed_user", reviewedUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    if (e == null) {
                        builder = new AlertDialog.Builder(UserEval.this);
                        builder.setMessage("Ya habías hecho una reseña sobre este usuario. Deseas actualizar " +
                                "tu reseña?");

                        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (score1 >= 0 && score1 <= 5) {
                                    String description = desc.getText().toString();

                                    //object.put("offer_title", offerTitle);
                                    object.put("reviewed_user", reviewedUser);
                                    object.put("reviewed_by", ParseUser.getCurrentUser().getUsername());
                                    object.put("description", description);
                                    object.put("score", score1);
                                    object.saveInBackground();
                                    Toast.makeText(getApplicationContext(), "Reseña enviada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "La puntuación debe estar entre 1 y 5.", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    if (score1 >= 0 && score1 <= 5) {
                        String description = desc.getText().toString();

                        ParseObject review = new ParseObject("Reviews");
                        //review.put("offer_title", offerTitle);
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
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(intent);
    }
}