package com.carpark.assessment.Service;

import com.carpark.assessment.Model.AvailabilityInfo;
import com.carpark.assessment.Model.LocationInfo;
import com.carpark.assessment.Repository.AvailabilityRepository;
import com.carpark.assessment.Repository.LocationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class AvailabilityService {

    static final String availability_url = "https://api.data.gov.sg/v1/transport/carpark-availability?date_time=";
    static final String ZONE_ID = "Asia/Shanghai";
    static final String CAR_PARK_DATA = "carpark_data";
    static final String CAR_PARK_NUMBER = "carpark_number";
    static final String CAR_PARK_INFO = "carpark_info";
    static final String TOTAL_LOTS = "total_lots";
    static final String LOTS_AVAILABLE = "lots_available";

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ClientCallService clientCallService;

    public void availabiltyData() throws Exception {
        deleteAllRecords();

        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(ZONE_ID));
        String uri = new StringBuilder(availability_url).append(dateTime).toString();
        JSONObject availabilityObject = clientCallService.fetchData(uri);
        JSONArray carParkArray = availabilityObject.getJSONArray("items")
                .getJSONObject(0)
                .getJSONArray(CAR_PARK_DATA);

        createAvailabiltyRecords(carParkArray);
    }

    private void createAvailabiltyRecords(JSONArray carParkArray) throws Exception {
        int total_cars = carParkArray.length();
        for (int it = 0; it < total_cars; it++) {
            try {
                JSONObject info_obj = carParkArray.getJSONObject(it);
                String update_datetime = info_obj.getString("update_datetime");
                JSONArray availabilty_info = info_obj.getJSONArray(CAR_PARK_INFO);

                Optional<LocationInfo> carpark_optional = locationRepository.findById(info_obj.getString(CAR_PARK_NUMBER));
                if (!carpark_optional.isPresent()) {
                    continue;
                }
                LocationInfo car_park_obj = carpark_optional.get();

                int slots_num = availabilty_info.length()-1;
                Integer totalSlots = 0;
                Integer availableSlots = 0;

                while(slots_num >= 0) {
                    totalSlots = Integer.valueOf(availabilty_info.getJSONObject(slots_num).getString(TOTAL_LOTS));
                    availableSlots = Integer.valueOf(availabilty_info.getJSONObject(slots_num).getString(LOTS_AVAILABLE));
                    slots_num--;
                }
                AvailabilityInfo availabilityInfo = new AvailabilityInfo(car_park_obj, totalSlots, availableSlots, update_datetime);
                availabilityRepository.save(availabilityInfo);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    private void deleteAllRecords() {
        availabilityRepository.deleteAll();
    }
}

