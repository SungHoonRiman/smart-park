package com.test.smartpark.controller;

import com.test.smartpark.dto.ParkingLotDto;
import com.test.smartpark.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lots")

public class ParkingLotController {

    @Autowired
    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public ResponseEntity<ParkingLotDto> registerParkingLot(@RequestBody ParkingLotDto lot) {
        ParkingLotDto result = parkingLotService.registerParkingLot(lot);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLotDto> getParkingLot(@PathVariable String lotId) {
        return ResponseEntity.ok(parkingLotService.getParkingLot(lotId));
    }

}
