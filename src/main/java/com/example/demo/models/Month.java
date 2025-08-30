package com.example.demo.models;

import java.util.List;

public class Month {
    
    //all of the next one, could probably be just a bunch of returns from methods.
    //or even get them to a different class/handler.

    private double goal = 0;
    private String name;
    //the mean of the holidaySells

    
    

    //bd  relations
    private List<Day> days;


    public Month(){

    }

    


    //pseudo gets of total amount of sells by period of time

    public double totalSells(){
        double totalSells = 0;
        for (Day day : days) {
            totalSells += day.getTotalSells();
        }
        return totalSells;
    }

   public double totalMorningSells(){
        double totalMorningSells = 0;
        for (Day day : days) {
            totalMorningSells += day.getMorningSells();
        }
        return totalMorningSells;
    }

   public double totalAfternoonSells(){
        double totalAfternoonSells = 0;
        for (Day day : days) {
            totalAfternoonSells += day.getAfternoonSells();
        }
        return totalAfternoonSells;
    }

    public double remainingSells(){
        return this.goal - this.totalSells();
    }
    //END

    //special cases for days passed
    public int daysPassed(){
        int daysPassed = 0;
        for (Day day : days) {
            if (day.getTotalSells() != 0.0) daysPassed++;
        }
        return daysPassed;
    }
    public int HdDaysPassed(){
        int HdDaysPassed = 0;
        for (Day day : days) {
            if (day.getTotalSells() != 0.0  && day.getHoliday() == true) HdDaysPassed++;
        }
        return HdDaysPassed;
    }

    public int BdDaysPassed(){
        int BdDaysPassed = 0;
        for (Day day : days) {
            if (day.getTotalSells() != 0.0  && day.getHoliday() == false) BdDaysPassed++;
        }
        return BdDaysPassed;
    }
    //END

    //special cases of amount of sells

    public double totalHolidaySells(){
        double totalHolidaySells = 0;
        for (Day day : days) {
            if (day.getHoliday() == true) totalHolidaySells += day.getTotalSells();
        }
        return totalHolidaySells;
    }


    public double totalBdSells(){
        double totalBdSells = 0;
        for (Day day : days) {
            if (day.getHoliday() == false) totalBdSells += day.getTotalSells();
        }
        return totalBdSells;
    }


    
    //END

    //means and arithmetic thinggys
    public double averageSells(){
        if (daysPassed()==0) return 0;
        return totalSells()/daysPassed();
    }
    public double averageBDsells(){
        if (BdDaysPassed()==0) return 0;
        return totalBdSells()/BdDaysPassed();
    }
    public double averageHDsells(){
        if (HdDaysPassed() == 0) return 0;
        return totalHolidaySells()/HdDaysPassed();
    }


    public double averageMorningSells(){
        if (daysPassed() == 0) return 0;
        return totalMorningSells()/daysPassed();
            
    
    }
    public double averageAfternoonSells(){
        if (daysPassed() == 0) return 0;
        return totalAfternoonSells()/daysPassed();
            
        
    }

    //Predictions

    public double remainingDays(){
        if (this.goal == 0 ) return 0;
        return remainingSells()/averageSells();
    }

    //TODO prediction by hd and bd
    //END

    //getters and setters
    public double getGoal(){
        return this.goal;
    }
    public void setGoal(double goal){
        this.goal = goal;
    }


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public List<Day> getDays(){
        return days;
    }
    public void setDays(List<Day> days){
        this.days = days;
    }
    //END
}
