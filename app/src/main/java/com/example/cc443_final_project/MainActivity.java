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
    private final Account account = new Account();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        createAccount = (Button) findViewById(R.id.create_account_button);
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        createAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_account_button:
                startActivity(new Intent(this, createAccount.class));
                break;
            case R.id.login:
                loginUser();
                break;
            case R.id.forgot_password:
                account.sendResetLink();
                break;
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty())
        {
            editTextEmail.setError("You must enter a valid email!");
            return;
        }
        if (password.isEmpty())
        {
            editTextPassword.setError("You must enter this field!");
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to user profile
                    startActivity(new Intent(MainActivity.this, homePage.class));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login. Try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

