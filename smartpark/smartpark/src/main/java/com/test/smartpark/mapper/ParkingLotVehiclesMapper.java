package com.test.smartpark.mapper;

import com.test.smartpark.dto.ParkingLotVehiclesDto;
import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.entity.Vehicle;

import java.util.List;

public class ParkingLotVehiclesMapper {
    public static ParkingLotVehiclesDto toDto(ParkingLot parkingLot, List<Vehicle> vehicles) {
        return new ParkingLotVehiclesDto(
                parkingLot.getLotId(),
                VehicleMapper.toDtoList(vehicles)
        );
    }
}
