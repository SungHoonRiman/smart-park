package com.test.smartpark.service;

import com.test.smartpark.dto.ParkingLotVehiclesDto;
import com.test.smartpark.dto.VehicleDto;
import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.entity.Vehicle;
import com.test.smartpark.mapper.ParkingLotVehiclesMapper;
import com.test.smartpark.mapper.VehicleMapper;
import com.test.smartpark.repository.ParkingLotRepository;
import com.test.smartpark.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.test.smartpark.utility.MessageConstants.*;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;

    public VehicleDto registerVehicle(VehicleDto dto) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(dto.getLicensePlate());

        if (existingVehicle.isPresent()) {
            throw new IllegalArgumentException(LICENSE_PLATE_EXISTS);
        }
        Vehicle vehicle = VehicleMapper.toEntity(dto);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return VehicleMapper.toDto(savedVehicle);
    }

    public String checkInVehicle(String lotId, String licensePlate) {
        String responseMessage;
        ParkingLot lot = parkingLotRepository.findById(lotId).orElseThrow(() -> new EntityNotFoundException(PARKING_LOT_NOT_FOUND));

        if (lot.isFull()) {
            throw new IllegalStateException(PARKING_FULL);
        }
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND));

        if (vehicle.getParkingLot() != null) {
            responseMessage = VEHICLE_ALREADY_PARKED;
            throw new IllegalStateException(VEHICLE_ALREADY_PARKED);
        }

        vehicle.setParkingLot(lot);
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() + 1);
        vehicleRepository.save(vehicle);
        parkingLotRepository.save(lot);
        responseMessage = VEHICLE_CHECKED_IN;

        return responseMessage;
    }

    public String checkOutVehicle(String licensePlate) {
        String responseMessage;
        Vehicle vehicle = vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_PARKED));

        if (vehicle.getParkingLot() == null) {
            responseMessage = VEHICLE_NOT_PARKED;
            throw new IllegalStateException(VEHICLE_NOT_PARKED);
        }

        ParkingLot lot = vehicle.getParkingLot();
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);
        vehicle.setParkingLot(null);
        vehicleRepository.save(vehicle);
        parkingLotRepository.save(lot);

        responseMessage = VEHICLE_CHECKED_OUT;
        return responseMessage;

    }

    public List<ParkingLotVehiclesDto> getVehiclesByParkingLot(String lotId) {

        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new EntityNotFoundException(PARKING_LOT_NOT_FOUND));
        List<Vehicle> vehicles = vehicleRepository.findByParkingLot(parkingLot);

        return Collections.singletonList(ParkingLotVehiclesMapper.toDto(parkingLot, vehicles));
    }
}
