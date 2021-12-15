package com.example.cc443_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

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
        }
        if (reEnterPassword.isEmpty() || reEnterPassword != password)
        {
            editTextReEnterPassword.setError("Passwords must match!");
        }
    }
}