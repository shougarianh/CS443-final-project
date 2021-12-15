package com.example.cc443_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class createAccount extends AppCompatActivity {
    private FirebaseAuth mAuth; // part of checking current Auth state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser() {
        String email = "TODO" ; // convert to string using edit.getText().
    }
}