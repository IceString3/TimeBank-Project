package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();

    RadioButton rButtonOffers;
    RadioButton rButtonRequests;
    ArrayAdapter<String> arrayAdapter;
    String currentCommunity = ParseUser.getCurrentUser().getString("community_name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("Lista de usuarios");

        rButtonOffers = (RadioButton) findViewById(R.id.radioButtonO);
        rButtonRequests = (RadioButton) findViewById(R.id.radioButtonR);

        ListView userListView = findViewById(R.id.userListView);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (rButtonOffers.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), OfferListActivity.class);
                    intent.putExtra("username_r", users.get(i));
                    startActivity(intent);
                } else if (rButtonRequests.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), RequestListActivity.class);
                    intent.putExtra("username_r", users.get(i));
                    startActivity(intent);
                } else {
                    Toast.makeText(UserListActivity.this, "No se ha seleccionado ninguna opción",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        users.clear();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("community_name", currentCommunity);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {
                            users.add(user.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}