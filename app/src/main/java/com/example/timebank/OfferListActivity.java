package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class OfferListActivity extends AppCompatActivity {

    /*TODO
    User can edit their own offers.
     */

    int j;

    ParseUser user1 = new ParseUser();
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(
            2);

    ArrayList<OfferClass> offersArray = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        String username1 = getIntent().getStringExtra("username_r");

        setTitle("Lista de ofertas");

        ListView offerListView = findViewById(R.id.offerListView);

        offerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Intent intent = new Intent(getApplicationContext(), OfferContent.class);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
                String offerID = offersArray.get(i).getId();
                j = i;
                query.getInBackground(offerID, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            intent.putExtra("o_id", offersArray.get(j).getId());
                            intent.putExtra("o_username", offersArray.get(j).getUser());
                            intent.putExtra("o_title", offersArray.get(j).getTitle());
                            intent.putExtra("o_description", offersArray.get(j).getDesc());
                            intent.putExtra("o_oneTime", offersArray.get(j).isOneTime());
                            startActivity(intent);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        offersArray.clear();

        // El array "from" especifica qué claves del "mapa"
        // queremos ver en la "ListView"
        String[] from = { "line1", "line2" };

        // El array "to" especifica los "TextViews" desde el esquema xml donde queremos
        // mostrar los valores definidos en el array "from"
        int[] to = { android.R.id.text1, android.R.id.text2 };

        // Crea el adaptador y lo asigna a la "ListView"
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);
        offerListView.setAdapter(adapter);

        ParseQuery<ParseObject> queryOffer = ParseQuery.getQuery("Offer");

        if (username1 != null) {
            queryOffer.whereEqualTo("community", ParseUser.getCurrentUser().getString("community_name"));
            queryOffer.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (int i = 0; i < objects.size(); i++) {
                                ParseObject offer = objects.get(i);
                                OfferClass offers = new OfferClass();
                                try {
                                    if (offer.getParseUser("username").fetchIfNeeded().getUsername().equals(username1)) {
                                        offers.setUser(username1);
                                        offers.setId(offer.getObjectId());
                                        offers.setTitle(offer.getString("offer_title"));
                                        offers.setDesc(offer.getString("offer_desc"));
                                        offers.setOneTime(offer.getBoolean("one_time_only"));

                                        offersArray.add(offers);

                                        HashMap<String, String> map1 = new HashMap<>();
                                        map1.put("line1", offer.getString("offer_title"));
                                        map1.put("line2", offer.getString("offer_desc"));
                                        list.add(map1);
                                    }
                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
        } else {
            queryOffer.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject offer : objects) {
                                OfferClass offers = new OfferClass();
                                try {
                                    ParseUser user = offer.getParseUser("username").fetchIfNeeded();
                                    offers.setUser(user.getUsername());
                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }

                                offers.setId(offer.getObjectId());
                                offers.setTitle(offer.getString("offer_title"));
                                offers.setDesc(offer.getString("offer_desc"));
                                offers.setOneTime(offer.getBoolean("one_time_only"));

                                offersArray.add(offers);

                                HashMap<String, String> map1 = new HashMap<>();
                                map1.put("line1", offer.getString("offer_title") + " "  + "(" + offers.getUser() + ")");
                                map1.put("line2", offer.getString("offer_desc"));
                                list.add(map1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}