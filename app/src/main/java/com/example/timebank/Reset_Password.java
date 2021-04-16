package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class Reset_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        EditText editTextForgotPwd = findViewById(R.id.editTextEmailResetPwd);
        final String forgotPwdText = editTextForgotPwd.getText().toString();
        Button buttonResetPwd = findViewById(R.id.buttonResetPwd);
        buttonResetPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseUser.requestPasswordResetInBackground(forgotPwdText, new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "El email para la" +
                                    " recuperación de la contraseña ha sido enviado", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("Reset_password", "Error: " + e.getMessage());
                        }
                    }
                });

            }
        });
    }
}