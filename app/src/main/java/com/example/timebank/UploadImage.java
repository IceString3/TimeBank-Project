package com.example.timebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class UploadImage extends AppCompatActivity {

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            ParseFile file = new ParseFile("image.png", byteArray);
                            object.put("image", file);
                            object.put("username", ParseUser.getCurrentUser().getUsername());
                            object.saveInBackground();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } else {
                    if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            Log.i("Image Selected", "Good work");
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            ParseFile file = new ParseFile("image.png", byteArray);
                            ParseObject newObject = new ParseObject("Image");
                            newObject.put("image", file);
                            newObject.put("username", ParseUser.getCurrentUser().getUsername());
                            newObject.saveInBackground();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }
}