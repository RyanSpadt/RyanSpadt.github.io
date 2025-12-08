package com.example.cs360project.model;

public class Weight {

    private int id;
    private String date;
    private String weight;
    private int user_id;

    // For creating new weight entries with no ID
    public Weight(String date, String weight, int user_id) {
        this.date = date;
        this.weight = weight;
        this.user_id = user_id;
    }

    // For retrieval of weight objects from db
    public Weight(int id, String date, String weight, int user_id) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.user_id = user_id;
    }

    public int getID() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }

    public String getWeight() {
        return this.weight;
    }

    public int getUserID() {
        return this.user_id;
    }
}
