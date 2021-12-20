package com.example.cc443_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends Activity implements View.OnClickListener{
    private Button createAccount, loginButton;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        // initialize firebase authentication reference
        mAuth = FirebaseAuth.getInstance();
        // initialize all ui components
        createAccount = (Button) findViewById(R.id.create_account_button);
        loginButton = (Button) findViewById(R.id.login);
        // set click listener for the login button
        loginButton.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        createAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // new user, go to create account page
            case R.id.create_account_button:
                startActivity(new Intent(this, createAccount.class));
                break;
            // returning user, login
            case R.id.login:
                loginUser();
                break;
        }
    }

    // helper function, logs in user
    private void loginUser() {
        // prune the input from the edit texts from whitespaces and save them to strings
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        // make sure email field has been filled
        if (email.isEmpty())
        {
            editTextEmail.setError("You must enter a valid email!");
            return;
        }
        // make sure password field has been filled
        if (password.isEmpty())
        {
            editTextPassword.setError("You must enter this field!");
        }
        // sign the user in with the firebase api call
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to user profile
                    startActivity(new Intent(MainActivity.this, homePage.class));
                } else {
                    // notify the user that login was not successful
                    Toast.makeText(MainActivity.this, "Failed to login. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

