package com.example.cc443_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class homePage extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private Button addWorkoutButton;
    private MenuItem logoutButton;
    private MenuItem accountButton;
    private NavigationView navigationView;
    private FirebaseAuth myAuth;
    private ListView list;
    private DatabaseReference reference;
    private ArrayAdapter<String> adapter; // for modifying list view
    private ArrayList<String> items = new ArrayList<>(); // contains all of our local workouts
    private Map<String, String> firebaseWorkoutIDs = new HashMap<>(); // will store firbase workout ids

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        //////////////// THIS BLOCK IS FOR RETRIEVING WORKOUT DATA FROM FIREBASE //////////////////
        list = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("Workouts")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Workout workout = dataSnapshot.getValue(Workout.class);
                    String workoutItem = "Type: " + workout.getType() + "\nLength: " + workout.getLength()
                            + "\nCalories: " + workout.getCalories() + "\nDate: " + workout.getDateString();
                    items.add(workoutItem);
                    String key = String.valueOf(items.indexOf(workoutItem));
                    String workoutID = dataSnapshot.getKey().toString();
                    firebaseWorkoutIDs.put(key, workoutID);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // get the id of the workout from the hash map, key is the position
                String workoutID = firebaseWorkoutIDs.get(String.valueOf(position));
                reference.child(workoutID).removeValue();
                items.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addWorkoutButton = findViewById(R.id.add_workout);
        // Set up the navigation view
        navigationView = (NavigationView) findViewById(R.id.navmenu);
        logoutButton = navigationView.getMenu().findItem(R.id.nav_logout);
        accountButton = navigationView.getMenu().findItem(R.id.nav_account);
        myAuth = FirebaseAuth.getInstance();

        logoutButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myAuth.signOut();
                startActivity(new Intent(homePage.this, MainActivity.class));
                return true;
            }
        });

        accountButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(homePage.this, Account.class));
                return true;
            }
        });

        myAuth = FirebaseAuth.getInstance();


        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, addWorkout.class));
            }
        });


        //// DATABASE TESTING/////////


        /////////////////////////////




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}