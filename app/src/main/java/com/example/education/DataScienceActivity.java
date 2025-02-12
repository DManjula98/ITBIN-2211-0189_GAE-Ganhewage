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

public class DataScienceActivity extends AppCompatActivity {
    private ListView listView;

    //list of web developing topics
    private String[] topics = {
            "Introduction to Data Science",
            "Machine Learning Basics",
            "Data Visualization Techniques",
            "Big Data & Analytics",
            "Deep Learning Fundamentals"
    };
    private String[] descriptions = {
            "Data Science is an interdisciplinary field that uses scientific methods to extract knowledge from data.",
            "Machine learning is a branch of AI that enables systems to learn from data without being explicitly programmed.",
            "Data visualization helps interpret complex datasets using graphical representations.",
            "Big Data involves managing and analyzing massive amounts of data for business insights.",
            "Deep Learning is a subset of machine learning focused on artificial neural networks."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_science);


        //get listview by id
        listView = findViewById(R.id.lst3);

        //create an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item,topics);

        //set adapter to the listview

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(DataScienceActivity.this, TopicDetailsActivity.class);
            intent.putExtra("topicTitle", topics[position]);
            intent.putExtra("topicDescription", descriptions[position]);
            startActivity(intent);
        });

    }
}