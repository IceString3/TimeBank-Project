package com.example.timebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class new_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        final ParseUser user = new ParseUser();

        Button nextScr = findViewById(R.id.buttonNext);

        nextScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameText = findViewById(R.id.editTextName);
                EditText surnameText = findViewById(R.id.editTextSurname);
                EditText surnameText2 = findViewById(R.id.editTextSurname2);
                EditText locText = findViewById(R.id.editTextLoc);
                EditText professionText = findViewById(R.id.editTextProfession);
                EditText servicesText = findViewById(R.id.editTextServices);

                user.put("name", nameText.getText().toString());
                user.put("surname1", surnameText.getText().toString());
                user.put("surname2", surnameText2.getText().toString());
                user.put("location", locText.getText().toString());
                user.put("profession", professionText.getText().toString());
                user.put("services", servicesText.getText().toString());
                user.saveInBackground();

//                final String name = nameText.getText().toString();
//                String surname1 = surnameText.getText().toString();
//                String surname2 = surnameText2.getText().toString();
//                String phone = phoneText.getText().toString();
//                String mobilePhone = mobilePhoneText.getText().toString();
//                String loc = locText.getText().toString();
//                String profession = professionText.getText().toString();
//                String services = servicesText.getText().toString();

                Intent intent = new Intent(new_account.this,
                        new_account2.class);
                intent.putExtra("Name", nameText.getText().toString());
                intent.putExtra("Surname1", surnameText.getText().toString());
                intent.putExtra("Surname2", surnameText2.getText().toString());
                intent.putExtra("Location", locText.getText().toString());
                intent.putExtra("Profession", professionText.getText().toString());
                intent.putExtra("Services", servicesText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
