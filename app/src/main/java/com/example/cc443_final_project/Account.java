package com.example.cc443_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {

    private Button resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);

        TextView email = findViewById(R.id.current_user_view);

        // https://stackoverflow.com/questions/54784101/firebase-android-get-current-user-email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText(user.getEmail());
        }

        resetPassword = (Button) findViewById(R.id.change_password);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo
            }
        });

    }
}