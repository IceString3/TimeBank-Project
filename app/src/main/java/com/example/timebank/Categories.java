package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.parse.ParseObject;

public class Categories extends AppCompatActivity {

    String[] categoriesArray;
    int arraySize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        String comingFrom = getIntent().getStringExtra("taskCreated");

        CheckBox[] categories = {findViewById(R.id.checkBoxCarpentry), findViewById(R.id.checkBoxCar),
                findViewById(R.id.checkBoxGardening), findViewById(R.id.checkBoxPainting),
                findViewById(R.id.checkBoxPlumbing), findViewById(R.id.checkBoxCooking),
                findViewById(R.id.checkBoxHousekeeping), findViewById(R.id.checkBoxSewing),
                findViewById(R.id.checkBoxBeauty), findViewById(R.id.checkBoxChildcare),
                findViewById(R.id.checkBoxClasses), findViewById(R.id.checkBoxComputers),
                findViewById(R.id.checkBoxLanguages), findViewById(R.id.checkBoxFinances),
                findViewById(R.id.checkBoxMisc), findViewById(R.id.checkBoxMedServ),
                findViewById(R.id.checkBoxNutrition), findViewById(R.id.checkBoxFitness),
                findViewById(R.id.checkBoxYoga), findViewById(R.id.checkBoxCounseling),
                findViewById(R.id.checkBoxRecycling), findViewById(R.id.checkBoxComServ),
                findViewById(R.id.checkBoxGroupProjects), findViewById(R.id.checkBoxFundraising),
                findViewById(R.id.checkBoxSpProjects), findViewById(R.id.checkBoxOthers)};
        Button accept = findViewById(R.id.btnAcceptCategories);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j = 0;
                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].isChecked()) {
                        arraySize++;
                    }
                }
                categoriesArray = new String[arraySize];
                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].isChecked()) {
                        categoriesArray[j] = categories[i].getText().toString();
                        j++;
                    }
                }
                if (comingFrom != null) {
                    if (comingFrom.equals("offer")) {
                        Intent intent = new Intent(Categories.this, OfferCreate.class);
                        intent.putExtra("categories", categoriesArray);
                        startActivity(intent);
                    } else if (comingFrom.equals("request")) {
                        Intent intent = new Intent(Categories.this, RequestCreate.class);
                        intent.putExtra("categories", categoriesArray);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido determinar la pantalla anterior",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Categories.this, MainMenu.class);
                    startActivity(intent);
                }
            }
        });
    }
}