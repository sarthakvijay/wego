package com.carpark.assessment;

import com.carpark.assessment.Service.LocationService;
import com.carpark.assessment.Service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AssessmentApplication {

	@Autowired
	AvailabilityService availabilityService;

	@Autowired
	LocationService locationService;

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	/** If storing or persisting data in memory database only, than when we start appplication we should load data first.
	 * Though PostConstruct annotation is depreciated but serves the purpose here.
	 * **/
	@PostConstruct
	public void loadingDataAfterStartup() throws Exception {
//		locationService.locationData();
//		availabilityService.availabiltyData();
	}



}
