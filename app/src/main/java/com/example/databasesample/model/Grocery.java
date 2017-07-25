package com.example.databasesample.model;

public class Grocery {
    public String name;

    public int amount;

    public String unit;

    public boolean bought;

    public Grocery(String name, int amount, String unit, boolean bought) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.bought = bought;
    }
}
