package ca.georgebrown.comp3074.uiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button config, faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

        config = findViewById(R.id.config);
        config.setOnClickListener(v -> startNewActivity(ConfigurationActivity.class));

        faq = findViewById(R.id.faq);
        faq.setOnClickListener(v -> startNewActivity(FAQActivity.class));
    }

    private void startNewActivity(Class<?> activityClass) {
        Intent intent = new Intent(SettingsActivity.this, activityClass);
        startActivity(intent);
    }
}
