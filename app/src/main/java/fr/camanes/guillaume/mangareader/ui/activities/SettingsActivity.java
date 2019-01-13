package fr.camanes.guillaume.mangareader.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;

import fr.camanes.guillaume.mangareader.R;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkboxStorageType;

    public static final String SHARED_PREF_STORAGE = "settings_storagePath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkboxStorageType = findViewById(R.id.checkbox_storagetype);

        checkboxStorageType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);

                SharedPreferences.Editor editor = mSettings.edit();

                boolean saveUserAnswers = buttonView.isChecked();

                String savedStoragePath = getStoragePath(saveUserAnswers);
                Log.e("storage path", savedStoragePath);

                editor.putString(SHARED_PREF_STORAGE, savedStoragePath);

                editor.apply();
            }
        });

        String storagePath = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).
                getString(SHARED_PREF_STORAGE, Environment.getExternalStorageDirectory().getAbsolutePath());

        checkboxStorageType.setChecked(!storagePath.equals(Environment.getExternalStorageDirectory().getAbsolutePath()));
    }

    private String getStoragePath(boolean storagePreference) {

        if (storagePreference) {
            File fileList[] = new File("/storage/").listFiles();
            for (File file : fileList) {
                if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead()) {
                    return file.getAbsolutePath();
                }
            }
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }
}
