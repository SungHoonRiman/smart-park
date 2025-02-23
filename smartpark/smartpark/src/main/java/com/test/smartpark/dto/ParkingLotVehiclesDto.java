package com.test.smartpark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Data
public class ParkingLotVehiclesDto {
    private String parkingLot;
    private List<VehicleDto> vehicles;
    
}
