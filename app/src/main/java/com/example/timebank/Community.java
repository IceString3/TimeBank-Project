package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Community extends AppCompatActivity {


    EditText editTextCommunityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        editTextCommunityName = findViewById(R.id.editTextCommunityName);

    }

    public void onCreate(View v) {
        final String communityName = editTextCommunityName.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("community_name", communityName);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        ParseUser.getCurrentUser().put("community_name", communityName);
                        ParseObject community = new ParseObject("Community");
                        community.put("community_name", communityName);
                        community.put("first_user", ParseUser.getCurrentUser().getUsername());
                        community.saveInBackground();
                        ParseUser.getCurrentUser().saveInBackground();
                        Toast.makeText(getApplicationContext(), "La primera comunidad " +
                                communityName + " ha sido creada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ContentMain.class);
                        startActivity(intent);
                    } else {
                        objects.size();
                        for (ParseObject communityList : objects) {
                            if (communityList.getString("community_name").equals(communityName)) {
                                Toast.makeText(getApplicationContext(), "La comunidad ya existe.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                ParseUser.getCurrentUser().put("community_name", communityName);
                                ParseObject community = new ParseObject("Community");
                                community.put("community_name", communityName);
                                community.saveInBackground();
                                ParseUser.getCurrentUser().saveInBackground();
                                Toast.makeText(getApplicationContext(), "La comunidad " +
                                        communityName + " ha sido creada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ContentMain.class);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    Log.i("Community create", "Error: " + e.getMessage());
                }
            }
        });
    }
    public void onJoin(View v) {
        final String communityName = editTextCommunityName.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("community_name", communityName);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No hay ninguna comunidad",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        objects.size();
                        for (ParseObject communityList : objects) {
                            if (communityList.getString("community_name").equals(communityName)) {
                                ParseUser.getCurrentUser().put("community_name", communityName);
                                ParseUser.getCurrentUser().saveInBackground();
                                Toast.makeText(getApplicationContext(), "Se ha unido a la comunidad de " +
                                        communityName, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ContentMain.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha encontrado la comunidad " +
                                        communityName, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Log.i("Community join", "Error: " + e.getMessage());
                }
            }
        });
    }
}