package com.carpark.assessment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Response {
    Double latitude;

    Double longitude;

    Integer total_lots;

    Integer available_lots;

    String address;
}
