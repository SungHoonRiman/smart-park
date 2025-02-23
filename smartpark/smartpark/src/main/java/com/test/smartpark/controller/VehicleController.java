package com.test.smartpark.controller;

import com.test.smartpark.dto.ParkingLotVehiclesDto;
import com.test.smartpark.dto.StringResponseDto;
import com.test.smartpark.dto.VehicleDto;
import com.test.smartpark.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDto> registerVehicle(@RequestBody VehicleDto vehicle) {
        VehicleDto result = vehicleService.registerVehicle(vehicle);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{lotId}/check-in/{licensePlate}")
    public ResponseEntity<StringResponseDto> checkIn(@PathVariable String lotId, @PathVariable String licensePlate) {
        String message = vehicleService.checkInVehicle(lotId, licensePlate);
        return ResponseEntity.ok(new StringResponseDto(message));
    }

    @PostMapping("/check-out/{licensePlate}")
    public ResponseEntity<StringResponseDto> checkOut(@PathVariable String licensePlate) {
        String message = vehicleService.checkOutVehicle(licensePlate);
        return ResponseEntity.ok(new StringResponseDto(message));
    }

    @GetMapping("lot/{lotId}")
    public List<ParkingLotVehiclesDto> getVehiclesByParkingLot(@PathVariable String lotId) {
        return vehicleService.getVehiclesByParkingLot(lotId);
    }

}
