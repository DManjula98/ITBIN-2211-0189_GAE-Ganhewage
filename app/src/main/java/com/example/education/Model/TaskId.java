package com.example.education.Model;

import androidx.annotation.NonNull;
import com.google.firebase.firestore.Exclude;

public class TaskId {
    @Exclude
    public String taskId;

    public <T extends TaskId> T withId(@NonNull final String id) {
        this.taskId = id;
        return (T) this;
    }
}
