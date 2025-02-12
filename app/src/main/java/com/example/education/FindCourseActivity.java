package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_course);

        //link cloud computing subject
        CardView cloudcomputing= findViewById(R.id.courseTwo);
        cloudcomputing.setOnClickListener(view -> {
            Intent intent = new Intent(FindCourseActivity.this,CloudComputingActivity.class);
            startActivity(intent);

        });

        //link web development subject
        CardView web= findViewById(R.id.CourseOne);
        web.setOnClickListener(view -> {
            Intent intent = new Intent(FindCourseActivity.this,WebDevelopmentActivity.class);
            startActivity(intent);

        });

        //link data science subject
        CardView data= findViewById(R.id.CourseThree);
        data.setOnClickListener(view -> {
            Intent intent = new Intent(FindCourseActivity.this,DataScienceActivity.class);
            startActivity(intent);

        });

        //link networking subject
        CardView net= findViewById(R.id.CourseFour);
        net.setOnClickListener(view -> {
            Intent intent = new Intent(FindCourseActivity.this,NetworkingActivity.class);
            startActivity(intent);

        });

        //log out
        Button logoutButton = findViewById(R.id.buttonLogOut);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(FindCourseActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

    }
}