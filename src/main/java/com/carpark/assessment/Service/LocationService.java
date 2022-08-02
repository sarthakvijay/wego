package com.carpark.assessment.Service;

import com.carpark.assessment.Model.LocationInfo;
import com.carpark.assessment.Repository.LocationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LocationService {

    static final String CONVERSION_URL = "https://developers.onemap.sg/commonapi/convert/3414to4326?";
    static final String LOCATION_URL = "https://data.gov.sg/api/action/datastore_search?resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c";
    static final String LIMIT_PARAM = "&limit=";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    static final String X_PARAM = "X=";
    static final String Y_PARAM = "&Y=";

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ClientCallService clientCallService;

    public void locationData() throws Exception {
        deleteAllRecords();

		JSONObject location_data = clientCallService.fetchData(LOCATION_URL);
        String limit = ((JSONObject)location_data.get("result")).get("total").toString();
        String limit_uri = new StringBuilder(LOCATION_URL).append(LIMIT_PARAM).append(limit).toString();

        location_data = clientCallService.fetchData(limit_uri);
        JSONArray location_records_array = ((JSONObject)location_data.get("result")).getJSONArray("records");

        createLocationRecords(location_records_array);
    }

    private void createLocationRecords(JSONArray location_records_array) throws Exception {
        int total_record = location_records_array.length();
        for(int it=0;it<total_record;it++){
            JSONObject record = location_records_array.getJSONObject(it);

            String address = record.getString("address");
            String number = record.getString("car_park_no");

            JSONObject latitude_longitude = conversion(record.getString("x_coord"), record.getString("y_coord"));
            Double latitude = latitude_longitude.getDouble(LATITUDE);
            Double longitude = latitude_longitude.getDouble(LONGITUDE);

            LocationInfo locationInfo = new LocationInfo(number, longitude, latitude, address);
            locationRepository.save(locationInfo);
        }
    }

    public JSONObject conversion(String x_coordinate, String y_coordinate) throws Exception {
        String uri = new StringBuilder(CONVERSION_URL)
                    .append(X_PARAM).append(x_coordinate)
                    .append(Y_PARAM).append(y_coordinate)
                    .toString();
        JSONObject jsonObject = clientCallService.fetchData(uri);
        return jsonObject;
    }

    private void deleteAllRecords(){
        locationRepository.deleteAll();
    }
}
