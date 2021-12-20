package com.example.cc443_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class addWorkout extends AppCompatActivity {

    private EditText typeOfWorkout;
    private EditText workoutLength;
    private EditText caloriesBurned;
    private EditText date;
    private Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_workout);
        // initialize ui components
        typeOfWorkout = (EditText) findViewById(R.id.workout_type);
        workoutLength = (EditText) findViewById(R.id.workout_length);
        caloriesBurned = (EditText) findViewById(R.id.calories_burned);
        date = (EditText) findViewById(R.id.date);
        done = (Button) findViewById(R.id.add_workout_to_log_button);
        // when the done button is clicked
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trim all white spaces and save all fields to strings
                String type = typeOfWorkout.getText().toString().trim();
                String length = workoutLength.getText().toString().trim();
                String calories = caloriesBurned.getText().toString().trim();
                String dateString = date.getText().toString().trim();
                // make sure that date matches a date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // make sure that all the fields are filled out
                if (type.isEmpty())
                {
                    typeOfWorkout.setError("Must input a value!");
                    return;
                }
                if (length.isEmpty())
                {
                    workoutLength.setError("Must input a value!");
                    return;
                }
                if (calories.isEmpty())
                {
                    caloriesBurned.setError("Must input a value!");
                    return;
                }
                if (dateString.isEmpty())
                {
                    date.setError("Must input a value!");
                    return;
                }
                try {
                    dateFormat.parse(dateString);
                } catch (ParseException e) {
                    date.setError("Date must be in dd/mm/yyyy format!");
                    return;
                }
                // create a new workout object
                Workout workout = new Workout(type, length, calories, dateString);
                // access the database, go into the workouts part, find the current user id
                // and add the new workout
                FirebaseDatabase.getInstance().getReference("Workouts")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .push().setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            // if it is successful, go back to home page and notify user
                            Toast.makeText(addWorkout.this, "Successfully logged workout!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(addWorkout.this, homePage.class));
                        }
                        else {
                            // otherwise notify user that workout couldn't be added
                            Toast.makeText(addWorkout.this, "Failed to log workout!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


    }
}