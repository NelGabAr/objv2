package com.example.demo.services;

import com.example.demo.models.Day;
import com.example.demo.models.Month;
import com.example.demo.models.Year;
import com.example.demo.repositories.MonthRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.*;



@Service
public class MonthService {

    private final MonthRepo monthRepo;

    public MonthService(MonthRepo monthRepo){
        this.monthRepo = monthRepo;
    }

    public Month save(Month month){
    return monthRepo.save(month);
    }
    
    public Month saveNewMonth(Year year){
        Month month = new Month();
        month.setYear(year);
        return monthRepo.save(month);
    }

    public Month getById(Long id){
        Month month = monthRepo.findById(id)
                                .orElseThrow(()-> new RuntimeException("mes no encontrado"));
                                
        return month;
    }

    public Month uploadMonth(Month month, List<Double> totalSells, List<Double> morningSells, List<Double> afternoonSells, List<String> holidays, Double goal){
        List<Day> days = month.getDays();
        month.setGoal(goal);

        for(int i=0;i<days.size();i++){
            month.getDays().get(i).setTotalSells(totalSells.get(i));
            month.getDays().get(i).setMorningSells(morningSells.get(i));
            month.getDays().get(i).setAfternoonSells(afternoonSells.get(i));
            month.getDays().get(i).setHoliday(Boolean.valueOf(holidays.get(i)));
            month.getDays().get(i).setSelected(prueba(holidays).get(i));
            
        }
        month = save(month);

        return update(month);
    }

    public List<String> prueba(List<String> lista) {
        List<String> retorno = new ArrayList<>();
        for (String list : lista) {
            if(list.equals("true")){
                retorno.add("selected");
            }
            else{
                retorno.add(" ");
            }
        }


        return retorno;
    }

    public Month update(Month month){

        month.totalSells();
        month.totalMorningSells();
        month.totalAfternoonSells();

        month.totalBdSells();
        month.totalHolidaySells();

        
        return monthRepo.save(month);
    }
    

}