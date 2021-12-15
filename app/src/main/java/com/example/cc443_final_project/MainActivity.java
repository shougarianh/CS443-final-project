package com.example.cc443_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{
    private Button createAccount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        createAccount = (Button) findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_account_button:
                startActivity(new Intent(this, createAccount.class));
                break;
        }
    }
}

