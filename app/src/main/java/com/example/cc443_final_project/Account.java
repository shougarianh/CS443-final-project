package com.example.cc443_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Account extends AppCompatActivity {

    private Button resetPassword;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);

        TextView email = findViewById(R.id.current_user_view);

        // https://stackoverflow.com/questions/54784101/firebase-android-get-current-user-email
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText(user.getEmail());
        }

        // reset password button
        resetPassword = (Button) findViewById(R.id.change_password);

        // when reset password button clicked, invoke helper function
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    // reset password helper function
    private void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // retrieve email address
        String email_address = Objects.requireNonNull(user.getEmail()).trim();
        // send reset password email
        auth.sendPasswordResetEmail(email_address)
                // alert user if behavior was successful
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Account.this, "We have sent you " +
                                    "instructions to reset your password!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Account.this, "Failed to send reset " +
                                    "email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}