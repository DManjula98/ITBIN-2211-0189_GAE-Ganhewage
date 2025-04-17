package com.example.education.Model;


public class TaskModel {
    private String id;
    private String task;
    private String fromTime;
    private String toTime;
    private String color;

    public TaskModel() {
        // Empty constructor required for Firestore
    }

    public TaskModel(String id, String task, String fromTime, String toTime, String color) {
        this.id = id;
        this.task = task;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.color = color;
    }

    public String getId() { return id; }
    public String getTask() { return task; }
    public String getFromTime() { return fromTime; }
    public String getToTime() { return toTime; }
    public String getColor() { return color; }
}
