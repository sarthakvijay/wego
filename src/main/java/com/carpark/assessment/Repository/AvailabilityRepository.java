package com.carpark.assessment.Repository;

import com.carpark.assessment.Model.AvailabilityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityInfo, Long> {
}
