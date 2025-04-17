
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    //create object UI component
    EditText edUsername, edEmail, edPassword, edConfirm;
    Button btn;
    TextView tv;
    FirebaseAuth mAuth;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegUsername);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        tv.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirm = edConfirm.getText().toString().trim();

                //validation check
                if(TextUtils.isEmpty(username)){
                    edUsername.setError("Username is Required");
                    return;
                }

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
                if(!password.equals(confirm)){
                    edConfirm.setError("Passwords do not match");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;

                                    if (userId != null) {
                                        // This saves the data to the Users collection in Firestore, with the userId as the document ID.
                                        DocumentReference userRef = db.collection("Users").document(userId);
                                        Map<String, Object> user = new HashMap<>();  // FIXED Object type
                                        //store these values in a Map to insert into Firestore
                                        user.put("username", username);
                                        user.put("email", email);
                                        user.put("userId", userId);

                                        userRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> firestoreTask) {
                                                if (firestoreTask.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Error Saving Data: " + firestoreTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
