package com.example.education.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.AddNewTask;
import com.example.education.Model.ToDoModel;
import com.example.education.R;
import com.example.education.ToDoListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> todoList;
    private ToDoListActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(ToDoListActivity activity, List<ToDoModel> todoList) {
        this.todoList = todoList;
        this.activity = activity;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDateTv.setText("Due On " + toDoModel.getDue());
        holder.mCheckBox.setChecked(toDoModel.getStatus() != 0);

        holder.mCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            firestore.collection("Users").document(userId).collection("tasks")
                    .document(toDoModel.taskId)
                    .update("status", isChecked ? 1 : 0);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void deleteTask(int position) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ToDoModel toDoModel = todoList.get(position);

        firestore.collection("Users").document(userId).collection("tasks")
                .document(toDoModel.taskId)
                .delete();

        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position) {
        ToDoModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task", toDoModel.getTask());
        bundle.putString("due", toDoModel.getDue());
        bundle.putString("id", toDoModel.taskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    public Context getContext() {
        return activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
