package com.example.cc443_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createAccount extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth; // part of checking current Auth state
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextReEnterPassword;
    private Button registerButton;
    private boolean signedUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextReEnterPassword = (EditText) findViewById(R.id.re_entered_password);
        registerButton = (Button) findViewById(R.id.create_account);
        registerButton.setOnClickListener(this);

    }

    public void onClick(View view) {
        registerUser();
    }

    public void registerUser() {
        // tutorial for this is on youtube at
        // https://www.youtube.com/watch?v=Z-RE1QuUWPg
        // trim the input for any leading or trailing white spaces
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String reEnterPassword = editTextReEnterPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Username is required!");
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address!");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            return;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(password);
        boolean b = m.find();

        if (password.length() < 8 || !b)
        {
            editTextPassword.setError("Password must contain atleast 8 characters and one " +
                    "special character");
            return;
        }
        if (reEnterPassword.isEmpty() || !reEnterPassword.equals(password))
        {
            editTextReEnterPassword.setError("Passwords must match!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            signedUp = true;
                            /*
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue("HELLO").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(createAccount.this, "Succesfully registered!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(createAccount.this, "Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                             */

                        }
                        else {
                            signedUp = false;
                            Toast.makeText(createAccount.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        }

                    }
        });
        if (signedUp) {
            startActivity(new Intent(this, homePage.class));
        }
    }
}