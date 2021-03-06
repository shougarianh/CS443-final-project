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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createAccount extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth; // part of checking current Auth state
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextReEnterPassword;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        // get a reference to firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // initialize all ui components
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextReEnterPassword = (EditText) findViewById(R.id.re_entered_password);
        registerButton = (Button) findViewById(R.id.create_account);
        // set a click listener for the create account button
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

        // make sure all fields have a valid input
        if (username.isEmpty()) {
            editTextUsername.setError("Username is required!");
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            return;
        }
        // make sure email matches an email pattern
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address!");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            return;
        }
        // make a pattern for password atleast one special character
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(password);
        boolean b = m.find();

        if (password.length() < 8 || !b)
        {
            editTextPassword.setError("Password must contain at least 8 characters and one " +
                    "special character");
            return;
        }
        if (reEnterPassword.isEmpty() || !reEnterPassword.equals(password))
        {
            editTextReEnterPassword.setError("Passwords must match!");
            return;
        }
        // use firebase api call to register the user with their email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // if it is successful, make a new user object
                            User user = new User(username, email);
                            // place the user object into the real time database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        // if successfully put into the database, notify user
                                        Toast.makeText(createAccount.this, "Succesfully registered!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        // otherwise, also notify user
                                        Toast.makeText(createAccount.this, "Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            // switch the activity to the main screen
                            startActivity(new Intent(createAccount.this, homePage.class));
                        }
                        else {
                            // notify user that their account was not created
                            Toast.makeText(createAccount.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        }

                    }
        });
    }
}