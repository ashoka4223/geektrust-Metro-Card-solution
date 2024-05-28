package com.example.geektrust.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class PassengerSummery {

    private HashMap<String,Passenger> passengerMap;
    private List<PassengerCheckIn> checkInList;
    private Integer totalAmountAirport;
    private Integer totalAmountCentral;
    private Integer totalDiscountAirport;
    private Integer totalDiscountCentral;
    private List<String> orderByTypeAirport;
    private List<String> orderByTypeCentral;

    public PassengerSummery(){
        this.passengerMap= new HashMap<>();
        this.checkInList =new ArrayList<>();
        this.totalAmountAirport=0;
        this.totalAmountCentral=0;
        this.totalDiscountAirport=0;
        this.totalDiscountCentral=0;
        this.orderByTypeAirport = new ArrayList<>();
        this.orderByTypeCentral = new ArrayList<>();
    }
}
