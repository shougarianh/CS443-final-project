package com.example.cc443_final_project;
public class Workout {
    public String type, length, calories, dateString;

    public Workout(String type, String length, String calories, String dateString) {
        this.type = type;
        this.length = length;
        this.calories = calories;
        this.dateString = dateString;
    }

    public String getType()
    {
        return type;
    }
    public String getLength()
    {
        return length;
    }
    public String getCalories(){
        return calories;
    }
    public String getDateString()
    {
        return dateString;
    }
}
