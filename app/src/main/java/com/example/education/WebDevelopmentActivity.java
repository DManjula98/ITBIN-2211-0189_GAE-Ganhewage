package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WebDevelopmentActivity extends AppCompatActivity {
    private ListView listView;

    private String[] topics = {
            "Introduction to Web Development",
            "HTML and CSS Basics",
            "JavaScript and DOM Manipulation",
            "Backend Development with Node.js",
            "Database Management in Web Development"
    };

    private String[] descriptions = {
            "Web development is the process of creating websites and web applications using various technologies.",
            "HTML structures web pages, and CSS styles them to enhance user experience.",
            "JavaScript adds interactivity to web pages, manipulating elements dynamically.",
            "Backend development involves server-side scripting using Node.js and handling data processing.",
            "Databases store and manage web application data, commonly using SQL or NoSQL solutions."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_development);

        //get listview by id
        ListView listView = findViewById(R.id.lst2);

        //create an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item,topics);

        //set adapter to the listview

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(WebDevelopmentActivity.this, TopicDetailsActivity.class);
            intent.putExtra("topicTitle", topics[position]);
            intent.putExtra("topicDescription", descriptions[position]);
            startActivity(intent);
        });
    }
}