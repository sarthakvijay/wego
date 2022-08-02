package com.carpark.assessment.Model;

import lombok.Getter;

@Getter
public class Pair implements Comparable<Pair>{
    Double distance;
    AvailabilityInfo availabilityInfo;

    public Pair(Double distance, AvailabilityInfo availabilityInfo){
        this.distance = distance;
        this.availabilityInfo = availabilityInfo;
    }

    @Override
    public int compareTo(Pair o) {
        return (this.distance).compareTo(o.distance);
    }
}
