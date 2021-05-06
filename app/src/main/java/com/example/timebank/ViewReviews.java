package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewReviews extends AppCompatActivity {

    AlertDialog.Builder builder;
    ArrayList<String> reviews = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        ListView reviewList = findViewById(R.id.reviewList);
        reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
                query.whereEqualTo("reviewed_by", reviews.get(i));
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        builder = new AlertDialog.Builder(ViewReviews.this);
                        builder.setTitle(object.getString("reviewed_by"));
                        builder.setMessage(object.getString("description") + "\n" + "Puntuación: " + object.getInt("score") + "/5");

                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        });

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviews);
        reviewList.setAdapter(arrayAdapter);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.whereEqualTo("reviewed_user", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject review : objects) {
                            reviews.add(review.getString("reviewed_by"));
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}