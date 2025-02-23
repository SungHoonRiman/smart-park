package com.test.smartpark.mapper;

import com.test.smartpark.dto.VehicleDto;
import com.test.smartpark.entity.Vehicle;
import com.test.smartpark.enums.VehicleType;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleMapper {

    public static Vehicle toEntity(VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setType(VehicleType.valueOf(String.valueOf(dto.getType())));
        vehicle.setOwnerName(dto.getOwnerName());
        return vehicle;
    }

    public static VehicleDto toDto(Vehicle entity) {
        VehicleDto dto = new VehicleDto();
        dto.setLicensePlate(entity.getLicensePlate());
        dto.setType(VehicleType.valueOf(String.valueOf(entity.getType())));
        dto.setOwnerName(entity.getOwnerName());
        return dto;
    }

    public static List<VehicleDto> toDtoList(List<Vehicle> entities) {
        return entities.stream().map(VehicleMapper::toDto).collect(Collectors.toList());
    }
}
