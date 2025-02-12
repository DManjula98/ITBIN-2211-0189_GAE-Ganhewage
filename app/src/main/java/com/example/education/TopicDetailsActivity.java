package com.example.education;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TopicDetailsActivity extends AppCompatActivity {

    private TextView textViewTitle,textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);

        // Get data from intent
        String topicTitle = getIntent().getStringExtra("topicTitle");
        String topicDescription = getIntent().getStringExtra("topicDescription");

        textViewTitle.setText(topicTitle);
        textViewDescription.setText(topicDescription);
    }
}
