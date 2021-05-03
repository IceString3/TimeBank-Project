package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.Collections;

public class UserAvailability extends AppCompatActivity {

    ParseObject availability = new ParseObject("Availability");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_availability);
        final CheckBox morningMo = findViewById(R.id.checkBoxMorningMo);
        final CheckBox morningTu = findViewById(R.id.checkBoxMorningTu);
        final CheckBox morningWe = findViewById(R.id.checkBoxMorningWe);
        final CheckBox morningTh = findViewById(R.id.checkBoxMorningTh);
        final CheckBox morningFr = findViewById(R.id.checkBoxMorningFr);
        final CheckBox morningSa = findViewById(R.id.checkBoxMorningSa);
        final CheckBox morningSu = findViewById(R.id.checkBoxMorningSu);

        final CheckBox middayMo = findViewById(R.id.checkBoxMiddayMo);
        final CheckBox middayTu = findViewById(R.id.checkBoxMiddayTu);
        final CheckBox middayWe = findViewById(R.id.checkBoxMiddayWe);
        final CheckBox middayTh = findViewById(R.id.checkBoxMiddayTh);
        final CheckBox middayFr = findViewById(R.id.checkBoxMiddayFr);
        final CheckBox middaySa = findViewById(R.id.checkBoxMiddaySa);
        final CheckBox middaySu = findViewById(R.id.checkBoxMiddaySu);

        final CheckBox afternoonMo = findViewById(R.id.checkBoxAfternoonMo);
        final CheckBox afternoonTu = findViewById(R.id.checkBoxAfternoonTu);
        final CheckBox afternoonWe = findViewById(R.id.checkBoxAfternoonWe);
        final CheckBox afternoonTh = findViewById(R.id.checkBoxAfternoonTh);
        final CheckBox afternoonFr = findViewById(R.id.checkBoxAfternoonFr);
        final CheckBox afternoonSa = findViewById(R.id.checkBoxAfternoonSa);
        final CheckBox afternoonSu = findViewById(R.id.checkBoxAfternoonSu);

        Button accept = findViewById(R.id.btnAcceptAvail);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Availability");
                query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            if (morningMo.isChecked()) {
                                object.addUnique("Monday", morningMo.getText());
                            } else {
                                object.removeAll("Monday", Arrays.asList(morningMo.getText()));
                            }
                            object.saveInBackground();
                            if (middayMo.isChecked()) {
                                object.addUnique("Monday", middayMo.getText());
                            } else {
                                object.removeAll("Monday", Arrays.asList(middayMo.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonMo.isChecked()) {
                                object.addUnique("Monday", afternoonMo.getText());
                            } else {
                                object.removeAll("Monday", Arrays.asList(afternoonMo.getText()));
                            }
                            object.saveInBackground();
                            if (morningTu.isChecked()) {
                                object.addUnique("Tuesday", morningTu.getText());
                            } else {
                                object.removeAll("Tuesday", Arrays.asList(morningTu.getText()));
                            }
                            object.saveInBackground();
                            if (middayTu.isChecked()) {
                                object.addUnique("Tuesday", middayTu.getText());
                            } else {
                                object.removeAll("Tuesday", Arrays.asList(middayTu.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonTu.isChecked()) {
                                object.addUnique("Tuesday", afternoonTu.getText());
                            } else {
                                object.removeAll("Tuesday", Arrays.asList(afternoonTu.getText()));
                            }
                            object.saveInBackground();
                            if (morningWe.isChecked()) {
                                object.addUnique("Wednesday", morningWe.getText());
                            } else {
                                object.removeAll("Wednesday", Arrays.asList(morningWe.getText()));
                            }
                            object.saveInBackground();
                            if (middayWe.isChecked()) {
                                object.addUnique("Wednesday", middayWe.getText());
                            } else {
                                object.removeAll("Wednesday", Arrays.asList(middayWe.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonWe.isChecked()) {
                                object.addUnique("Wednesday", afternoonWe.getText());
                            } else {
                                object.removeAll("Wednesday", Arrays.asList(afternoonWe.getText()));
                            }
                            object.saveInBackground();
                            if (morningTh.isChecked()) {
                                object.addUnique("Thursday", morningTh.getText());
                            } else {
                                object.removeAll("Thursday", Arrays.asList(morningTh.getText()));
                            }
                            object.saveInBackground();
                            if (middayTh.isChecked()) {
                                object.addUnique("Thursday", middayTh.getText());
                            } else {
                                object.removeAll("Thursday", Arrays.asList(middayTh.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonTh.isChecked()) {
                                object.addUnique("Thursday", afternoonTh.getText());
                            } else {
                                object.removeAll("Thursday", Arrays.asList(afternoonTh.getText()));
                            }
                            object.saveInBackground();
                            if (morningFr.isChecked()) {
                                object.addUnique("Friday", morningFr.getText());
                            } else {
                                object.removeAll("Friday", Arrays.asList(morningFr.getText()));
                            }
                            object.saveInBackground();
                            if (middayFr.isChecked()) {
                                object.addUnique("Friday", middayFr.getText());
                            } else {
                                object.removeAll("Friday", Arrays.asList(middayFr.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonFr.isChecked()) {
                                object.addUnique("Friday", afternoonFr.getText());
                            } else {
                                object.removeAll("Friday", Arrays.asList(afternoonFr.getText()));
                            }
                            object.saveInBackground();
                            if (morningSa.isChecked()) {
                                object.addUnique("Saturday", morningSa.getText());
                            } else {
                                object.removeAll("Saturday", Arrays.asList(morningSa.getText()));
                            }
                            object.saveInBackground();
                            if (middaySa.isChecked()) {
                                object.addUnique("Saturday", middaySa.getText());
                            } else {
                                object.removeAll("Saturday", Arrays.asList(middaySa.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonSa.isChecked()) {
                                object.addUnique("Saturday", afternoonSa.getText());
                            } else {
                                object.removeAll("Saturday", Arrays.asList(afternoonSa.getText()));
                            }
                            object.saveInBackground();
                            if (morningSu.isChecked()) {
                                object.addUnique("Sunday", morningSu.getText());
                            } else {
                                object.removeAll("Sunday", Arrays.asList(morningSu.getText()));
                            }
                            object.saveInBackground();
                            if (middaySu.isChecked()) {
                                object.addUnique("Sunday", middaySu.getText());
                            } else {
                                object.removeAll("Sunday", Arrays.asList(middaySu.getText()));
                            }
                            object.saveInBackground();
                            if (afternoonSu.isChecked()) {
                                object.addUnique("Sunday", afternoonSu.getText());
                            } else {
                                object.removeAll("Sunday", Arrays.asList(afternoonSu.getText()));
                            }
                            object.saveInBackground();
                        } else {
                            availability.put("username", ParseUser.getCurrentUser().getUsername());
                            if (morningMo.isChecked()) {
                                availability.add("Monday", morningMo.getText());
                            }
                            if (middayMo.isChecked()) {
                                availability.add("Monday", middayMo.getText());
                            }
                            if (afternoonMo.isChecked()) {
                                availability.add("Monday", afternoonMo.getText());
                            }
                            if (morningTu.isChecked()) {
                                availability.add("Tuesday", morningTu.getText());
                            }
                            if (middayTu.isChecked()) {
                                availability.add("Tuesday", middayTu.getText());
                            }
                            if (afternoonTu.isChecked()) {
                                availability.add("Tuesday", afternoonTu.getText());
                            }
                            if (morningWe.isChecked()) {
                                availability.add("Wednesday", morningWe.getText());
                            }
                            if (middayWe.isChecked()) {
                                availability.add("Wednesday", middayWe.getText());
                            }
                            if (afternoonWe.isChecked()) {
                                availability.add("Wednesday", afternoonWe.getText());
                            }
                            if (morningTh.isChecked()) {
                                availability.add("Thursday", morningTh.getText());
                            }
                            if (middayTh.isChecked()) {
                                availability.add("Thursday", middayTh.getText());
                            }
                            if (afternoonTh.isChecked()) {
                                availability.add("Thursday", afternoonTh.getText());
                            }
                            if (morningFr.isChecked()) {
                                availability.add("Friday", morningFr.getText());
                            }
                            if (middayFr.isChecked()) {
                                availability.add("Friday", middayFr.getText());
                            }
                            if (afternoonFr.isChecked()) {
                                availability.add("Friday", afternoonFr.getText());
                            }
                            if (morningSa.isChecked()) {
                                availability.add("Saturday", morningSa.getText());
                            }
                            if (middaySa.isChecked()) {
                                availability.add("Saturday", middaySa.getText());
                            }
                            if (afternoonSa.isChecked()) {
                                availability.add("Saturday", afternoonSa.getText());
                            }
                            if (morningSu.isChecked()) {
                                availability.add("Sunday", morningSu.getText());
                            }
                            if (middaySu.isChecked()) {
                                availability.add("Sunday", middaySu.getText());
                            }
                            if (afternoonSu.isChecked()) {
                                availability.add("Sunday", afternoonSu.getText());
                            }
                            availability.saveInBackground();
                        }
                    }
                });
            }
        });
    }
}