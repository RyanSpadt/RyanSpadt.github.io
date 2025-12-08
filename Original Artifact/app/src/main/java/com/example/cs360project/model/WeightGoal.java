package com.example.cs360project.model;

public class WeightGoal {

    private int id;
    private int user_id;
    private String goal;
    private String weight_when_set;
    private String type;

    // For creating new weight entries with no ID
    public WeightGoal(String goal, int user_id, String weight_when_set, String type) {
        this.goal = goal;
        this.user_id = user_id;
        this.weight_when_set = weight_when_set;
        this.type = type;
    }

    public int getUserID() {
        return this.user_id;
    }

    public String getWeightGoal() {
        return this.goal;
    }

    public String getGoalType() {
        return this.type;
    }
    
    public String getWeightWhenSet() {
        return this.weight_when_set;
    }
}
