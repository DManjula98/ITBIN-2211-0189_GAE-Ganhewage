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

public class CloudComputingActivity extends AppCompatActivity {
    private ListView listView;

    private String[] topics = {
            "Introduction to Cloud Computing",
            "Cloud Service Models (IaaS, PaaS, SaaS)",
            "Cloud Deployment Models (Public, Private, Hybrid)",
            "Benefits and Challenges of Cloud Computing",
            "Security in Cloud Computing"
    };

    private String[] descriptions = {
            "Cloud computing delivers computing services like servers, storage, databases, and more over the internet.",
            "IaaS provides virtualized computing resources, PaaS offers development platforms, and SaaS delivers software applications.",
            "Cloud can be deployed as Public (shared resources), Private (exclusive use), or Hybrid (mix of both).",
            "Cloud computing provides scalability and cost savings but also comes with security and compliance challenges.",
            "Security in the cloud involves data encryption, authentication, and monitoring for threats."
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cloud_computing);


        //get listview by id
        listView = findViewById(R.id.lst1);

        //create an adapter
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.list_item,topics);

        //set adapter to the listview

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(CloudComputingActivity.this, TopicDetailsActivity.class);
            intent.putExtra("topicTitle", topics[position]);
            intent.putExtra("topicDescription", descriptions[position]);
            startActivity(intent);
        });


    }
}