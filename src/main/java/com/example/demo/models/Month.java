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
        double result = Math.round(totalSells * 100.0) / 100.0;
        return result;
    }

    

    public double totalMorningSells(){
        double totalMorningSells = 0;
        for (Day day : days) {
            totalMorningSells += day.getMorningSells();
        }
        double result = Math.round(totalMorningSells * 100.0) / 100.0;
        return result;
        
    }



    public double totalAfternoonSells(){
        double totalAfternoonSells = 0;
        for (Day day : days) {
            totalAfternoonSells += day.getAfternoonSells();
        }

        double result = Math.round(totalAfternoonSells * 100.0) / 100.0;
        return result;
        
    }



    public double remainingSells(){

        return Math.round((this.goal - this.totalSells())*100)/100;
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
        double result = Math.round(totalHolidaySells * 100.0) / 100.0;
        return result;
    }

    public double totalBdSells(){
        double totalBdSells = 0;
        for (Day day : days) {
            if (day.getHoliday() == false) totalBdSells += day.getTotalSells();
        }

        double result = Math.round(totalBdSells * 100.0) / 100.0;
        return result;
    }



    //means and arithmetic thinggys
    public double averageSells(){
        if (daysPassed()==0) return 0;



        return Math.round((totalSells()/daysPassed()) * 100.0) / 100.0;
        
    }

    public double averageBDsells(){
        if (BdDaysPassed()==0) return 0;
        


        return Math.round((totalBdSells()/BdDaysPassed()) * 100.0) / 100.0;
    }

    public double averageHDsells(){
        if (HdDaysPassed() == 0) return 0;
        return Math.round((totalHolidaySells()/HdDaysPassed()) * 100.0) / 100.0;
    
        
    }

    public double averageMorningSells(){
        if (daysPassed() == 0) return 0;
        
        return Math.round((totalMorningSells()/daysPassed()) * 100.0) / 100.0;
    }
    
    public double averageAfternoonSells(){
        if (daysPassed() == 0) return 0;
        return Math.round((totalAfternoonSells()/daysPassed()) * 100.0) / 100.0;
    }
    



    //Predictions
    public double remainingDays(){
        if (this.goal == 0 ) return 0;
        if (this.averageSells()== 0) return 0;
        
        return Math.round((remainingSells()/averageSells()) * 100.0) / 100.0;
    }
    
    
    public int bdDaysToPass(){
        int aux = 0;
        for (Day day : days) {
            if(!day.getHoliday()&&day.getTotalSells()==0){
                aux++;
            }
        }

        return aux;
    }

public int hdDaysToPass(){
        int aux = 0;
        for (Day day : days) {
            if(day.getHoliday()&&day.getTotalSells()==0){
                aux++;
            }
        }

        return aux;
    }
    





    

    //prediction by hd and bd

    public double bdSellsNeeded(){

        if (totalBdSells() == 0.0 || totalHolidaySells() == 0.0 || this.goal==0.0)  {
            return 0.0;
        }

        double ratio = averageHDsells()/averageBDsells();


        if(bdDaysToPass() == 0){
            return 0;
        }
        double result= remainingSells() / (hdDaysToPass() * ratio  +bdDaysToPass());
        return Math.round(result*100)/100;


    }

    public double hdSellsNeeded(){

        if (totalBdSells() == 0.0 || totalHolidaySells() == 0.0 || this.goal==0.0)  {
            return 0.0;
        }
        if(hdDaysToPass()==0){
            return 0;
        }

        if(bdDaysToPass() == 0){
            return Math.round(remainingSells()/hdDaysToPass()*100)/100;
        }
        double ratio = averageHDsells()/averageBDsells();
        return Math.round(ratio*bdSellsNeeded()*100)/100;


    }



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
