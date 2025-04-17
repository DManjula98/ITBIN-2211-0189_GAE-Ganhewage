package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        //find timetable
        CardView findCourse= findViewById(R.id.cardCourse);
        findCourse.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this,TimeTableActivity.class);
            startActivity(intent);

        });

        // Find to_do list
        CardView toDoList = findViewById(R.id.cardTo_Do);
        toDoList.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ToDoListActivity.class);
            startActivity(intent);
        });

        // calculator
         CardView CourseArticle = findViewById(R.id.cardCalculator);
         CourseArticle.setOnClickListener(view -> {
             Intent intent = new Intent(HomeActivity.this, CalculatorActivity.class);
             startActivity(intent);
         });

         //student
        CardView StudentDetails= findViewById(R.id.cardStudent);
        StudentDetails.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, StudentDetailsActivity.class);
            startActivity(intent);
        });


        //logout button
        Button logoutButton = findViewById(R.id.LogOut);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });


    }
}