package com.example.cc443_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);

        TextView email = findViewById(R.id.current_user_view);

        // https://stackoverflow.com/questions/54784101/firebase-android-get-current-user-email
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText(user.getEmail());
        }

        resetPassword = findViewById(R.id.change_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResetLink();
            }
        });

    }

    // still testing...via. https://stackoverflow.com/questions/42800349/forgot-password-in-firebase-for-android
    public void sendResetLink() {
        assert user != null;
        String email = Objects.requireNonNull(user.getEmail()).trim();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Account.this, "We have sent you " +
                                        "instructions to reset your password!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Account.this, "Failed to send " +
                                "reset email!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}