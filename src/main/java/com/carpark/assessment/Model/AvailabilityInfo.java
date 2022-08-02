package com.carpark.assessment.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "INFO")
@Getter
@Setter
@NoArgsConstructor
public class AvailabilityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_number")
    LocationInfo locationInfo;

    @Column(name = "total_lots")
    Integer total_lots;

    @Column(name = "available_lots")
    Integer available_lots;

//    @Column(name = "type")
//    String lot_type;

    @Column(name = "update_time")
    String date;

    static final Double SEMI = 180.00;
    static final Double SIXTH = 60.00;
    static final Double MILES_FACTOR = 1.1515;

    public AvailabilityInfo(LocationInfo locationInfo, Integer total_lots, Integer available_lots, String date) {
        this.locationInfo = locationInfo;
        this.total_lots = total_lots;
        this.available_lots = available_lots;
        this.date = date;
    }

    public double distance(double current_latitude, double current_longitude) {
        double theta = current_longitude - this.locationInfo.getLongitude();
        double dist = Math.sin(deg2rad(current_latitude)) * Math.sin(deg2rad(this.locationInfo.getLatitude()))
                + Math.cos(deg2rad(current_latitude)) * Math.cos(deg2rad(this.locationInfo.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * SIXTH * MILES_FACTOR;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / SEMI);
    }

    private double rad2deg(double rad) {
        return (rad * SEMI / Math.PI);
    }

}
