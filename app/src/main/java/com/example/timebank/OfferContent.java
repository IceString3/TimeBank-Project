package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class OfferContent extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText editTextTitle;
    EditText editTextDesc;
    Button cancel;
    Button cancelEdit;
    Button acceptEdit;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_content);

        boolean isOneTime = getIntent().getExtras().getBoolean("o_oneTime");

        String offerID = getIntent().getExtras().getString("o_id");
        username = getIntent().getExtras().getString("o_username");
        String title = getIntent().getExtras().getString("o_title");
        String desc = getIntent().getExtras().getString("o_description");

        ParseQuery<ParseObject> queryImage = new ParseQuery<ParseObject>("Image");
        queryImage.whereEqualTo("username", username);
        queryImage.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ParseFile file = (ParseFile) object.get("image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null && data != null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView profileImage = findViewById(R.id.imageView);
                                    profileImage.setImageBitmap(bitmap);
                                }
                            }
                        });
                    }
                }
            }
        });


        TextView textViewName = findViewById(R.id.textViewRealname);
        TextView textViewAvail = findViewById(R.id.textViewAvail);
        editTextTitle = findViewById(R.id.editTextTitle1);
        editTextDesc = findViewById(R.id.editTextDesc1);

        editTextTitle.setEnabled(false);
        editTextDesc.setEnabled(false);

        cancel = findViewById(R.id.btnCancelOfferCont);
        cancelEdit = findViewById(R.id.btnCancelEdit);
        acceptEdit = findViewById(R.id.btnAcceptEdit);

        if (username.equals(ParseUser.getCurrentUser().getUsername())) {
            cancelEdit.setVisibility(View.VISIBLE);
            acceptEdit.setVisibility(View.VISIBLE);
            cancelEdit.setEnabled(false);
            acceptEdit.setEnabled(false);
        } else {
            cancelEdit.setVisibility(View.GONE);
            acceptEdit.setVisibility(View.GONE);
            cancelEdit.setEnabled(false);
            acceptEdit.setEnabled(false);
        }

        textViewName.setText(username);
        editTextTitle.setText(title);
        editTextDesc.setText(desc);

        if (isOneTime) {
            textViewAvail.setText("Una vez");
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
            query.getInBackground(offerID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String myDate = "31/12/2099 23:59:59";  // 4102444799000L
                        LocalDateTime localDateTime = LocalDateTime.parse(myDate,
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") );

                        long millis = localDateTime
                                .atZone(ZoneId.of("UTC"))
                                .toInstant().toEpochMilli();
                        Date date = new Date(millis);
                        try {
                            if (object.getDate("expires_at").equals(date)) {
                                textViewAvail.setText("Siempre");
                            } else {
                                textViewAvail.setText(object.getString("expires_at"));
                            }
                        } catch (NullPointerException exception){
                            System.out.println("Fecha de expiración vacía");
                            textViewAvail.setText("Siempre");
                        }
                    }
                }
            });
        }
    }

    public void cancel(View v) {
        Intent intent = new Intent(getApplicationContext(), OfferListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        if (username.equals(ParseUser.getCurrentUser().getUsername())) {
            menu.findItem(R.id.menu_edit).setVisible(true);
            menu.findItem(R.id.menu_delete).setVisible(true);
            menu.findItem(R.id.menu_contact).setVisible(false);
            menu.findItem(R.id.menu_exchange).setVisible(false);
        } else {
            menu.findItem(R.id.menu_edit).setVisible(false);
            menu.findItem(R.id.menu_delete).setVisible(false);
            menu.findItem(R.id.menu_contact).setVisible(true);
            menu.findItem(R.id.menu_exchange).setVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        int itemId = item.getItemId();
        if (itemId == R.id.menu_edit) {
            editTextTitle.setEnabled(true);
            editTextDesc.setEnabled(true);
            cancelEdit.setEnabled(true);
            acceptEdit.setEnabled(true);
            cancelEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getIntent().getExtras().getString("o_title");
                    String desc = getIntent().getExtras().getString("o_description");
                    editTextTitle.setText(title);
                    editTextDesc.setText(desc);
                    editTextTitle.setEnabled(false);
                    editTextDesc.setEnabled(false);
                    cancelEdit.setEnabled(false);
                    acceptEdit.setEnabled(false);
                }
            });
            acceptEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
                    String offerID = getIntent().getExtras().getString("o_id");
                    query.getInBackground(offerID, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.put("offer_title", editTextTitle.getText().toString());
                                object.put("offer_desc", editTextDesc.getText().toString());
                                object.saveInBackground();
                            }
                        }
                    });
                    editTextTitle.setEnabled(false);
                    editTextDesc.setEnabled(false);
                    cancelEdit.setEnabled(false);
                    acceptEdit.setEnabled(false);
                }
            });
            return true;

        } else if (itemId == R.id.menu_delete) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea eliminar la tarea ofertada?");

            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
                    String offerID = getIntent().getExtras().getString("o_id");
                    query.getInBackground(offerID, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            try {
                                object.delete();
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            return true;

        } else if (itemId == R.id.menu_contact) {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            String username = getIntent().getExtras().getString("o_username");
            if (username.equals(ParseUser.getCurrentUser().getUsername())) {
                Toast.makeText(getApplicationContext(), "No puedes contactar contigo mismo", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("username_r", username);
                startActivity(intent);
            }
            return true;

        } else if (itemId == R.id.menu_exchange) {
            Intent intent = new Intent(getApplicationContext(), TimeExchangeOffer.class);
            String username = getIntent().getExtras().getString("o_username");
            if (username.equals(ParseUser.getCurrentUser().getUsername())) {
                Toast.makeText(getApplicationContext(), "No puedes intercambiar tiempo contigo mismo", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("o_id", getIntent().getExtras().getString("o_id"));
                intent.putExtra("o_username", getIntent().getExtras().getString("o_username"));
                intent.putExtra("o_title", getIntent().getExtras().getString("o_title"));
                startActivity(intent);
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}