package com.example.demo.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;


    private int number;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Month> months;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public int getNumber(){
        return this.number;
    }
    public void setMonths(List<Month> months){
        this.months = months;
    }
    public List<Month> getMonths(){
        return this.months;
    }
    public Long getId(){
        return this.Id;
    }


}
