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
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_workout);

        typeOfWorkout = (EditText) findViewById(R.id.workout_type);
        workoutLength = (EditText) findViewById(R.id.workout_length);
        caloriesBurned = (EditText) findViewById(R.id.calories_burned);
        date = (EditText) findViewById(R.id.date);
        done = (Button) findViewById(R.id.add_workout_to_log_button);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = typeOfWorkout.getText().toString().trim();
                String length = workoutLength.getText().toString().trim();
                String calories = caloriesBurned.getText().toString().trim();
                String dateString = date.getText().toString().trim();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
                Workout workout = new Workout(type, length, calories, dateString);
                FirebaseDatabase.getInstance().getReference("Workouts")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .push().setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(addWorkout.this, "Succesfully logged workout!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(addWorkout.this, homePage.class));
                        }
                        else {
                            Toast.makeText(addWorkout.this, "Failed to log workout!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


    }
}