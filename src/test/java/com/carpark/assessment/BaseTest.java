package com.carpark.assessment;

import com.carpark.assessment.Service.ClientCallService;
import com.carpark.assessment.Service.LocationService;
import org.aspectj.lang.annotation.Before;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import static org.mockito.Mockito.when;

public class BaseTest {
    static final String uri1 = "https://data.gov.sg/api/action/datastore_search?resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c";
    static JSONObject location_data;

    @InjectMocks
    ClientCallService clientCallService;

    @BeforeEach
    protected void setupControllers() throws Exception {
        MockitoAnnotations.initMocks(this);
        location_data = clientCallService.fetchData(uri1);
    }

    @Test
    void testFetchData() throws Exception {
        JSONObject object = (JSONObject) location_data.get("result");
        Assert.isTrue(!object.toString().isEmpty());
        Assert.isInstanceOf(JSONArray.class, object.getJSONArray("records"));
    }

    @Test
    void testAvailabilityData() throws Exception {
        JSONObject object = (JSONObject) location_data.get("result");
        Assert.isTrue(object.getJSONArray("records").length() >= 0);

        Assert.hasLength(object.getJSONArray("records").getJSONObject(0).getString("address"));
        Assert.hasLength(object.getJSONArray("records").getJSONObject(0).getString("car_park_no"));
    }

//    @Test
//    void testConversionUrl() throws Exception {
//        JSONObject object = ((JSONObject) location_data.get("result")).getJSONArray("records").getJSONObject(0);
//        JSONObject latitude_longitude = locationService.conversion(object.getString("x_coord"), object.getString("y_coord"));
//        Assert.hasLength(latitude_longitude.getString("latitude"));
//        Assert.hasLength(latitude_longitude.getString("longitude"));
//    }

}
