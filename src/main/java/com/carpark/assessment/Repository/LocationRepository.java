package com.carpark.assessment.Repository;

import com.carpark.assessment.Model.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationInfo, String> {
    Optional<LocationInfo> findById(String id);
}
