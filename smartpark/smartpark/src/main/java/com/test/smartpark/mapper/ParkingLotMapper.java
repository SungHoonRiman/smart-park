package com.test.smartpark.mapper;

import com.test.smartpark.dto.ParkingLotDto;
import com.test.smartpark.entity.ParkingLot;


public class ParkingLotMapper {

    public static ParkingLot toEntity(ParkingLotDto dto) {
        ParkingLot lot = new ParkingLot();
        lot.setLotId(dto.getLotId());
        lot.setLocation(dto.getLocation());
        lot.setCapacity(dto.getCapacity());
        lot.setOccupiedSpaces(dto.getOccupiedSpaces());
        return lot;
    }

    public static ParkingLotDto toDto(ParkingLot entity) {
        ParkingLotDto dto = new ParkingLotDto();
        dto.setLotId(entity.getLotId());
        dto.setLocation(entity.getLocation());
        dto.setCapacity(entity.getCapacity());
        dto.setOccupiedSpaces(entity.getOccupiedSpaces());
        return dto;
    }
}
