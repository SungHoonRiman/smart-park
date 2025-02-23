package com.test.smartpark.repository;

import com.test.smartpark.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {
    boolean existsByLocation(String location);
    
}
