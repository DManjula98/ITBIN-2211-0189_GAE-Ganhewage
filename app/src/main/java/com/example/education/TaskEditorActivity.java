package com.example.education;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.education.Model.TaskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.UUID;

public class TaskEditorActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String taskId;
    private String selectedColor = "#FFFFFF"; // Default color
    private Spinner colorSpinner;
    private TextView colorBox, fromText, toText; // Preview box + Time fields

    // Color values
    private final String[] colorValues = {
            "#dddddd", "#facedb", "#a6daff", "#cef2ce",
            "#fca5ae", "#feda9c", "#eedaff", "#fffbb0"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        EditText taskInput = findViewById(R.id.task);
        fromText = findViewById(R.id.save_from_text);
        toText = findViewById(R.id.save_to_text);
        Button submitBtn = findViewById(R.id.submit);
        TextView deleteBtn = findViewById(R.id.delete);
        colorSpinner = findViewById(R.id.colorSpinner);
        colorBox = findViewById(R.id.colorBox); // Color preview box

        // Spinner for Colors
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.color_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedColor = colorValues[position];
                colorBox.setBackgroundColor(Color.parseColor(selectedColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set Click Listeners for Time Pickers
        fromText.setOnClickListener(v -> showTimePicker(fromText));
        toText.setOnClickListener(v -> showTimePicker(toText));

        submitBtn.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
                return;
            }
            String userId = currentUser.getUid();

            String task = taskInput.getText().toString();
            String fromTime = fromText.getText().toString();
            String toTime = toText.getText().toString();

            if (task.isEmpty() || fromTime.equals("Click Here") || toTime.equals("Click Here")) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            taskId = UUID.randomUUID().toString();
            TaskModel taskModel = new TaskModel(taskId, task, fromTime, toTime, selectedColor);

            // Updated Firestore path: "Students" collection instead of "Users"
            db.collection("Students") // Changed "Users" to "Students"
                    .document(userId)
                    .collection("Timetable")
                    .document(taskId)
                    .set(taskModel)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Task Added!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        deleteBtn.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
                return;
            }
            String userId = currentUser.getUid();

            if (taskId == null) {
                Toast.makeText(this, "No task selected", Toast.LENGTH_SHORT).show();
                return;
            }

            // Updated Firestore path: "Students" collection instead of "Users"
            db.collection("Students") // Changed "Users" to "Students"
                    .document(userId)
                    .collection("Timetable")
                    .document(taskId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Task Deleted!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    // Show TimePicker Dialog
    private void showTimePicker(TextView timeText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    timeText.setText(formattedTime);
                },
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }
}
