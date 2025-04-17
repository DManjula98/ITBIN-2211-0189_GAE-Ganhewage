package com.example.education;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;

public class FirestoreHelper {
    private static final String COLLECTION_NAME = "TimeTable"; // Firestore Collection
    private FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Method to add a task
    public void addTask(String task, String from, String to, String color) {
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("task", task);
        taskData.put("from", from);
        taskData.put("to", to);
        taskData.put("color", color);

        db.collection(COLLECTION_NAME).add(taskData)
                .addOnSuccessListener(documentReference -> Log.d("Firestore", "Task Added"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error Adding Task", e));
    }

    // Method to get all tasks
    public void getAllTasks() {
        db.collection(COLLECTION_NAME).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Firestore", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.e("Firestore", "Error Getting Tasks", task.getException());
                    }
                });
    }

    // Method to delete a task
    public void deleteTask(String documentId) {
        db.collection(COLLECTION_NAME).document(documentId).delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Task Deleted"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error Deleting Task", e));
    }
}
