package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Day {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    
    private double morningSells = 0.0;
    private double afternoonSells = 0.0;
    private double totalSells = 0.0;

    private boolean holiday = false;

    //bd relations 

    // many Days to one Month
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "month_id")
    private Month month;

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

    public void setMonth(Month month){
        this.month = month;
    }
    public Month getMonth(){
        return this.month;
    }

}
