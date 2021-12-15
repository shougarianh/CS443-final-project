package com.example.cc443_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homePage extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.add_workout:
                startActivity(new Intent(this, addWorkout.class));
        }
    }
}