package com.example.education;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.Adapter.ToDoAdapter;
import com.example.education.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    private List<ToDoModel> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do_list);

        // Initialize views and Firestore
        recyclerView = findViewById(R.id.recycleView);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();

        // Set up RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up FloatingActionButton to add new task
        mFab.setOnClickListener(view ->
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG)
        );

        // Initialize list and adapter
        mlist = new ArrayList<>();
        adapter = new ToDoAdapter(this, mlist);
        recyclerView.setAdapter(adapter);

        // Enable swipe-to-delete/edit
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Load task data
        showData();
    }

    private void showData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("Users").document(userId).collection("tasks")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) return;

                    mlist.clear(); // Important: clear list to avoid duplication

                    for (DocumentChange docChange : value.getDocumentChanges()) {
                        if (docChange.getType() == DocumentChange.Type.ADDED) {
                            String id = docChange.getDocument().getId();
                            ToDoModel task = docChange.getDocument().toObject(ToDoModel.class).withId(id);
                            mlist.add(task);
                        }
                    }

                    Collections.reverse(mlist); // Most recent tasks on top
                    adapter.notifyDataSetChanged();
                });
    }
}
