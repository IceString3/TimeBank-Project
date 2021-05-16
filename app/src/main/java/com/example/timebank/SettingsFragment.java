package com.example.timebank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
        return super.onPreferenceTreeClick(preference);
    }
}
