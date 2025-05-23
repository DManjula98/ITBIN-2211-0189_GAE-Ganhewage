package com.example.education.Model;

public class ToDoModel extends TaskId {
    private String task;
    private String due;
    private int status;

    // Default constructor required for Firestore
    public ToDoModel() {
    }

    public ToDoModel(String task, String due, int status) {
        this.task = task;
        this.due = due;
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
