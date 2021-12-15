package com.example.cc443_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class addWorkout extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_workout);
    }

    @Override
    public void onClick(View v) {
        addNewWorkout();
    }

    public void addNewWorkout() {

    }
}
