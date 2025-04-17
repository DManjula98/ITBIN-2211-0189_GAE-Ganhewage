package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.education.Adapter.TaskAdapter;
import com.example.education.Model.TaskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<TaskModel> taskList;
    private TaskAdapter adapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        ListView timetableList = findViewById(R.id.timetable_list);
        ImageView plusButton = findViewById(R.id.plus); // Get the plus button

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList);
        timetableList.setAdapter(adapter);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            loadTasks(userId);
        } else {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_LONG).show();
        }

        // Set click listener on the plus button
        plusButton.setOnClickListener(v -> {
            Intent intent = new Intent(TimeTableActivity.this, TaskEditorActivity.class);
            startActivity(intent);
        });
    }

    private void loadTasks(String userId) {
        db.collection("Students").document(userId) // Changed "Users" to "Students"
                .collection("Timetable")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    taskList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        TaskModel task = document.toObject(TaskModel.class);
                        taskList.add(task);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error loading tasks: ", e);
                });
    }
}