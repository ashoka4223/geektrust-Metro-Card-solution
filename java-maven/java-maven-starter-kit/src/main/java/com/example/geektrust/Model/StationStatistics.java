package com.example.geektrust.Model;

import lombok.Data;

@Data
public class StationStatistics {
    private Integer count;
    private String passengerType;
    private Integer totalCharge;
    private Integer totalDiscount;
    private Integer orderByType;

    public StationStatistics(String passengerType) {
        super();
        this.passengerType=passengerType;
        this.totalCharge=0;
        this.totalDiscount=0;
        this.count=0;
    }
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(this==obj) {
            return true;
        }
        if(obj==null || this.getClass()!=obj.getClass()) {
            return false;
        }
        StationStatistics stats=(StationStatistics)obj;;
        return this.count.equals(stats.count)
                && this.passengerType.equals(stats.passengerType)
                && this.totalCharge.equals(stats.totalCharge)
                && this.totalDiscount.equals(stats.totalDiscount);
    }
}
