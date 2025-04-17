package com.example.education.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.education.Model.TaskModel;
import com.example.education.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskModel> {
    private Context context;
    private List<TaskModel> taskList;

    public TaskAdapter(Context context, List<TaskModel> taskList) {
        super(context, R.layout.task, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task, parent, false);
        }

        TaskModel task = taskList.get(position);

        // Update to use single TextView for time range
        TextView timeRange = convertView.findViewById(R.id.time_range);
        TextView taskText = convertView.findViewById(R.id.task);

        // Set time range
        String formattedTime = task.getFromTime() + " - " + task.getToTime();
        timeRange.setText(formattedTime);

        // Set task text and background color
        taskText.setText(task.getTask());
        taskText.setBackgroundColor(Color.parseColor(task.getColor()));

        return convertView;
    }
}
