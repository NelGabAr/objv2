package com.example.demo.models;

import java.util.List;

public class Month {
    
    //all of the next one, could probably be just a bunch of returns from methods.
    //or even get them to a different class/handler.

    private double totalSells;
    private double remainingSells;

    private double averageSells;    //the graphic description of adding and dividing
    private double averageBDsells;  //the mean of business days
    private double averageHDsells;  //the mean of the holidaySells

    private String name;
    

    //bd  relations
    private List<Day> days;


    public Month(){

    }

}
