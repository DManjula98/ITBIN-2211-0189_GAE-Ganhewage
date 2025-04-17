
package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    Button btn;
    TextView tv;
    FirebaseAuth mAuth;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        //navigate to register Activity
        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();


                //validation check
                if(TextUtils.isEmpty(email)){
                    edEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    edPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 8){
                    edPassword.setError("Password must be 8 characters");
                    return;
                }
                // Authenticate user with Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //This gets the unique ID of the currently logged-in user.
                                    String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;

                                    if (userId != null) {
                                        // Retrieve user data from Firestore. This points to the document inside the Users collection using the userId.
                                        DocumentReference userRef = db.collection("Users").document(userId);
                                        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> firestoreTask) {
                                                if (firestoreTask.isSuccessful() && firestoreTask.getResult().exists()) {
                                                    String username = firestoreTask.getResult().getString("username");

                                                    Toast.makeText(LoginActivity.this, "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
