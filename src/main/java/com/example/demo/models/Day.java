package com.example.demo.models;



public class Day {
    
    private double morningSells;
    private double afternoonSells;
    private double totalSells;

    private boolean holiday;

    //bd relations 

    // many Days to one Month
    
    private Month month;

    public Day(Month month){
        this.month = month;
    }




//gets snd sets

    public double getMorningSells(){
        return morningSells;
    }

    public double getAfternoonSells(){
        return afternoonSells;
    }

    public double getTotalSells(){
        return totalSells;
    }

    public boolean getHoliday(){
        return holiday;
    }



    public void setMorningSells(double morningSells){
        this.morningSells = morningSells;
    }

    public void setAfternoonSells(double afternoonSells){
        this.afternoonSells = afternoonSells;
    }

    public void setTotalSells(double totalSells){
        this.totalSells = totalSells;
    }

    public void setHoliday(boolean holiday){
        this.holiday = holiday;
    }



}
