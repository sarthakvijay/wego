package com.carpark.assessment.Service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientCallService {

    public JSONObject fetchData(String uri) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            return new JSONObject(result);
        } catch(JSONException e) {
            throw new Exception("Car Park Data API returned improper object" + e);
        } catch(RestClientException e){
            throw new Exception("Car Park Data API not working or limit reached" + e);
        }
    }
}
