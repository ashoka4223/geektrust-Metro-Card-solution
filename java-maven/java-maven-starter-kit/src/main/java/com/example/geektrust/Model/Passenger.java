package com.example.geektrust.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Passenger {
    private String metroCardNumber;
    private Integer journeyCount;
    private Integer balanceInMetroCard;

    public Passenger(String id, Integer balance) {
        this.metroCardNumber=id;
        this.balanceInMetroCard=balance;
    }
}