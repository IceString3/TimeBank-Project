package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.parse.ParseUser;

public class UserData extends AppCompatActivity {

    EditText nameText;
    EditText surnameText;
    EditText surnameText2;
    EditText locText;
    EditText professionText;
    EditText servicesText;
    EditText emailText;
    Button save;
    Button cancel;
    ImageButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        nameText = findViewById(R.id.nameUserData);
        surnameText = findViewById(R.id.surnameUserData1);
        surnameText2 = findViewById(R.id.surnameUserData2);
        locText = findViewById(R.id.locationUserData);
        professionText = findViewById(R.id.professionUserData);
        servicesText = findViewById(R.id.servicesUserData);
        emailText = findViewById(R.id.emailUserData);

        edit = findViewById(R.id.btnEditUserData);

        save = findViewById(R.id.btnSaveUserData);
        cancel = findViewById(R.id.btnCancelUserData);
        setEditFalse();

        nameText.setText(ParseUser.getCurrentUser().getString("Name"));
        surnameText.setText(ParseUser.getCurrentUser().getString("Surname1"));
        surnameText2.setText(ParseUser.getCurrentUser().getString("Surname2"));
        locText.setText(ParseUser.getCurrentUser().getString("Location"));
        professionText.setText(ParseUser.getCurrentUser().getString("Profession"));
        servicesText.setText(ParseUser.getCurrentUser().getString("Services"));
        emailText.setText(ParseUser.getCurrentUser().getString("email"));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setEnabled(true);

                nameText.setEnabled(true);
                surnameText.setEnabled(true);
                surnameText2.setEnabled(true);
                locText.setEnabled(true);
                professionText.setEnabled(true);
                servicesText.setEnabled(true);
                emailText.setEnabled(true);
            }
        });
    }
    public void save(View v) {
        nameText = findViewById(R.id.nameUserData);
        surnameText = findViewById(R.id.surnameUserData1);
        surnameText2 = findViewById(R.id.surnameUserData2);
        locText = findViewById(R.id.locationUserData);
        professionText = findViewById(R.id.professionUserData);
        servicesText = findViewById(R.id.servicesUserData);
        emailText = findViewById(R.id.emailUserData);

        String name = nameText.getText().toString();
        String surname1 = surnameText.getText().toString();
        String surname2 = surnameText2.getText().toString();
        String location = locText.getText().toString();
        String profession = professionText.getText().toString();
        String services = servicesText.getText().toString();
        String email = emailText.getText().toString();

        ParseUser.getCurrentUser().put("Name", name);
        ParseUser.getCurrentUser().put("Surname1", surname1);
        ParseUser.getCurrentUser().put("Surname2", surname2);
        ParseUser.getCurrentUser().put("Location", location);
        ParseUser.getCurrentUser().put("Profession", profession);
        ParseUser.getCurrentUser().put("Services", services);
        ParseUser.getCurrentUser().put("email", email);
        ParseUser.getCurrentUser().saveInBackground();

        setEditFalse();
    }

    public void cancel(View v) {
        if (save.isEnabled()) {
            setEditFalse();
        } else {
            Intent intent = new Intent(getApplicationContext(), UserSettings.class);
            startActivity(intent);
        }
    }
    public void setEditFalse() {
        save.setEnabled(false);
        nameText.setEnabled(false);
        surnameText.setEnabled(false);
        surnameText2.setEnabled(false);
        locText.setEnabled(false);
        professionText.setEnabled(false);
        servicesText.setEnabled(false);
        emailText.setEnabled(false);
    }
}