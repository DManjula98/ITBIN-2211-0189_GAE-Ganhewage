package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentDetailsActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button updateButton, deleteButton;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        usernameEditText = findViewById(R.id.Username);
        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        confirmPasswordEditText = findViewById(R.id.Confirm);
        updateButton = findViewById(R.id.buttonUpdate);
        deleteButton = findViewById(R.id.buttonDelete);

        if (currentUser != null) {
            emailEditText.setText(currentUser.getEmail());
        }


        updateButton.setOnClickListener(view -> updateUserData());//update button
        deleteButton.setOnClickListener(view -> deleteUserData());//delete button

    }
//When the Delete button is clicked, it calls the deleteUserData() method.
    private void deleteUserData() {
        if (currentUser == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("Users").document(currentUser.getUid())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    currentUser.delete();
                    auth.signOut();
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, RegisterActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show());
    }

//When the Update button is clicked, it calls the updateUserData() method.
    private void updateUserData() {
        if (currentUser == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirm = confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);

        DocumentReference userRef = firestore.collection("Users").document(currentUser.getUid());
        userRef.set(user).addOnSuccessListener(aVoid -> Toast.makeText(this, "User data updated", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
    }
}

