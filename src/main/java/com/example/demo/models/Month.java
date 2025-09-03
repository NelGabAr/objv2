package com.example.demo.models;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Month {

    private double goal = 0;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    //bd  relations
    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Day> days;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "year_id")
    private Year year;

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
    public void setYear(Year year){
        this.year = year;
    }
    public Year getYear(){
        return this.year;
    }


}
