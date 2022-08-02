package com.carpark.assessment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LOCATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
    @Id
    @Column(name="id")
    String id;

    @Column(name = "longitude")
    Double longitude;

    @Column(name = "latitude")
    Double latitude;

    @Column(name = "address")
    String address;

}
