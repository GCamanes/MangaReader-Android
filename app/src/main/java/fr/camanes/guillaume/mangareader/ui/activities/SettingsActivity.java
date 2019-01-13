package fr.camanes.guillaume.mangareader.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import fr.camanes.guillaume.mangareader.R;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkboxStorageType;

    private static final String SHARED_PREF_STORAGE_TYPE = "settings_keepUserAnswersOnRefresh";

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
                editor.putBoolean(SHARED_PREF_STORAGE_TYPE, saveUserAnswers);

                editor.apply();
            }
        });

        checkboxStorageType.setChecked(
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).
                        getBoolean(SHARED_PREF_STORAGE_TYPE, false));

    }

}
