package com.test.smartpark.repository;

import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByParkingLot(ParkingLot parkingLot);

}
