package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    int j;

    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(
            2);

    ArrayList<RequestClass> requestsArray = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        setTitle("Request List");

        ListView requestListView = findViewById(R.id.requestListView);



        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Intent intent = new Intent(getApplicationContext(), RequestContent.class);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
                String requestID = requestsArray.get(i).getId();
                j = i;
                query.getInBackground(requestID, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            intent.putExtra("r_id", requestsArray.get(j).getId());
                            intent.putExtra("r_username", requestsArray.get(j).getUser());
                            intent.putExtra("r_title", requestsArray.get(j).getTitle());
                            intent.putExtra("r_description", requestsArray.get(j).getDesc());
                            intent.putExtra("r_oneTime", requestsArray.get(j).isOneTime());
                            startActivity(intent);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        requestsArray.clear();

        String[] from = { "line1", "line2" };

        int[] to = { android.R.id.text1, android.R.id.text2 };

        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);
        requestListView.setAdapter(adapter);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject request : objects) {
                            RequestClass requests = new RequestClass();
                            try {
                                ParseUser user = request.getParseUser("username").fetchIfNeeded();
                                requests.setUser(user.getUsername());
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }

                            requests.setId(request.getObjectId());
                            requests.setTitle(request.getString("request_title"));
                            requests.setDesc(request.getString("request_desc"));
                            requests.setOneTime(request.getBoolean("one_time_only"));

                            requestsArray.add(requests);

                            HashMap<String, String> map1 = new HashMap<>();
                            map1.put("line1", request.getString("request_title"));
                            map1.put("line2", request.getString("request_desc"));
                            list.add(map1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}