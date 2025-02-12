package com.example.education;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.Adapter.ToDoAdapter;
import com.example.education.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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

        recyclerView = findViewById(R.id.recycleView);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFab.setOnClickListener(view -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG));

        mlist = new ArrayList<>();
        adapter = new ToDoAdapter(this, mlist);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        showData();
        recyclerView.setAdapter(adapter);


    }

    private void showData() {
        firestore.collection("task").addSnapshotListener((value, error) -> {
            if (error != null || value == null) {
                return;
            }

            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String id = documentChange.getDocument().getId();
                    ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class);
                    toDoModel = toDoModel.withId(id); // Ensure the correct type
                    mlist.add(toDoModel);
                }
            }

            Collections.reverse(mlist);
            adapter.notifyDataSetChanged();
        });
    }
}
