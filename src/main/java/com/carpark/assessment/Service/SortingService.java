package com.carpark.assessment.Service;

import com.carpark.assessment.Model.AvailabilityInfo;
import com.carpark.assessment.Model.Pair;
import com.carpark.assessment.Model.Response;
import com.carpark.assessment.Repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SortingService {

    @Autowired
    AvailabilityRepository availabilityRepository;

    public List<Response> fetchData(Map<String, String> params) throws Exception {
        List<AvailabilityInfo> data = sortAndfetch(params);

        return data.stream().map(e ->
                        new Response(e.getLocationInfo().getLatitude(), e.getLocationInfo().getLongitude(),
                                e.getTotal_lots(), e.getAvailable_lots(), e.getLocationInfo().getAddress()))
                .collect(Collectors.toList());
    }

    private List<AvailabilityInfo> sortAndfetch(Map<String, String> params) throws Exception {
        try {
            List<AvailabilityInfo> all_data = availabilityRepository.findAll();

            Double current_latitude = Double.valueOf(params.get("latitude"));
            Double current_longitude = Double.valueOf(params.get("longitude"));
            Integer page = Integer.parseInt(params.get("page"));
            Integer per_page = Integer.parseInt(params.get("per_page"));

            List<Pair> pair_list = new ArrayList<>();

            for(AvailabilityInfo info: all_data){
                Double distance = info.distance(current_latitude, current_longitude);
                Pair pair = new Pair(distance, info);
                pair_list.add(pair);
            }
            Collections.sort(pair_list);

            List<AvailabilityInfo> result = pair_list.subList(per_page*(page-1), per_page*page).stream().map(e -> e.getAvailabilityInfo())
                    .collect(Collectors.toList());
            return result;
        } catch(IndexOutOfBoundsException e){
            throw new Exception("Provide proper page and per_page count", e);
        } catch(IllegalArgumentException e){
            throw new Exception("Illegal argument provided in the Url", e);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

}

