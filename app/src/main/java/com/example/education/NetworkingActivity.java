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

public class NetworkingActivity extends AppCompatActivity {

    private ListView listView;

    private String[] topics = {
            "Introduction to Networking",
            "Types of Networks (LAN, WAN, MAN)",
            "Network Topologies",
            "TCP/IP Model",
            "Network Security Basics"
    };
    private String[] descriptions = {
            "Networking connects computers and devices to share data and resources.",
            "LAN (Local Area Network), WAN (Wide Area Network), and MAN (Metropolitan Area Network) are different network types.",
            "Network topologies define the structure of a network, including star, bus, ring, and mesh layouts.",
            "The TCP/IP model has layers that guide communication over networks, including transport and internet layers.",
            "Network security protects data from threats using firewalls, encryption, and authentication."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_networking);


        //get listview by id
        ListView listView = findViewById(R.id.lst4);

        //create an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item,topics);

        //set adapter to the listview

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(NetworkingActivity.this, TopicDetailsActivity.class);
            intent.putExtra("topicTitle", topics[position]);
            intent.putExtra("topicDescription", descriptions[position]);
            startActivity(intent);
        });
    }
}