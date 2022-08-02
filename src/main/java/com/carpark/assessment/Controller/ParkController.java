package com.carpark.assessment.Controller;

import com.carpark.assessment.Model.Response;
import com.carpark.assessment.Service.AvailabilityService;
import com.carpark.assessment.Service.LocationService;
import com.carpark.assessment.Service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carparks")
public class ParkController {

    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    LocationService locationService;

    @Autowired
    SortingService sortingService;

    /**
     * Initially called - load parking location data into System
     * @return
     * @throws Exception
     */
    @GetMapping("/location")
    public ResponseEntity<?> getAllLocation() {
        try {
            locationService.locationData();
            return ResponseEntity.ok(HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Map parking availabilty data - Location data
     * @return
     * @throws Exception
     */
    @GetMapping("/availability")
    public ResponseEntity<?>  getAllAvailabilty(){
        try{
            availabilityService.availabiltyData();
            return ResponseEntity.ok(HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Get the per page nearest parking stations
     * @param params
     * @return
     * @throws Exception
     */
    @GetMapping("/nearest")
    public ResponseEntity<?> sortedStations(@RequestParam Map<String, String> params){
        try {
            List<Response> result = sortingService.fetchData(params);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }

}
