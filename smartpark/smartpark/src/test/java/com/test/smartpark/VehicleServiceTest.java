package com.test.smartpark;


import com.test.smartpark.controller.ParkingLotController;
import com.test.smartpark.dto.ParkingLotVehiclesDto;
import com.test.smartpark.dto.VehicleDto;
import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.entity.Vehicle;
import com.test.smartpark.enums.VehicleType;
import com.test.smartpark.repository.ParkingLotRepository;
import com.test.smartpark.repository.VehicleRepository;
import com.test.smartpark.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.test.smartpark.utility.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
public class VehicleServiceTest {
    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerVehicle_Success() {
        // Mock request
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setLicensePlate("ABC-1234");
        vehicleDto.setType(VehicleType.CAR);
        vehicleDto.setOwnerName("John Doe");

        // Expected entity
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");

        // Mock repo
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleDto result = vehicleService.registerVehicle(vehicleDto);

        assertEquals("ABC-1234", result.getLicensePlate());
        assertEquals(VehicleType.CAR, result.getType());
        assertEquals("John Doe", result.getOwnerName());
    }

    @Test
    void registerVehicle_LicenseExist_ShouldThrowException() {
        // Mock request
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setLicensePlate("ABC-1234");
        vehicleDto.setType(VehicleType.CAR);
        vehicleDto.setOwnerName("John Doe");

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");

        when(vehicleRepository.findById("ABC-1234")).thenReturn(Optional.of(vehicle));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehicleService.registerVehicle(vehicleDto);
        });

        assertEquals(LICENSE_PLATE_EXISTS, exception.getMessage());
    }

    @Test
    void checkInVehicle_Success() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(0);

        // Mock vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");

        // Mock repo response
        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById("ABC-1234")).thenReturn(Optional.of(vehicle));

        // check-in vehicle
        String result = vehicleService.checkInVehicle("PARK001", "ABC-1234");

        assertEquals(VEHICLE_CHECKED_IN, result);
        assertEquals(1, parkingLot.getOccupiedSpaces());
    }

    @Test
    void checkInVehicle_ParkingLotFull_ShouldThrowException() {
        // Mock full parking lot
        ParkingLot fullParkingLot = new ParkingLot();
        fullParkingLot.setLotId("PARK002");
        fullParkingLot.setCapacity(2);
        fullParkingLot.setOccupiedSpaces(2);

        when(parkingLotRepository.findById("PARK002")).thenReturn(Optional.of(fullParkingLot));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            vehicleService.checkInVehicle("PARK002", "XYZ-5678");
        });

        assertEquals(PARKING_FULL, exception.getMessage());
    }

    @Test
    void checkInVehicle_VehicleNotFound_ShouldThrowException() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(0);

        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById("XYZ-5678")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.checkInVehicle("PARK001", "XYZ-5678");
        });

        assertEquals(VEHICLE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void checkInVehicle_VehicleAlreadyParked_ShouldThrowException() {

        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(0);

        // Mock vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("XYZ-5678");
        vehicle.setType(VehicleType.MOTORCYCLE);
        vehicle.setOwnerName("John Doe");
        vehicle.setParkingLot(parkingLot);

        when(vehicleRepository.findById("XYZ-5678")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));


        Exception exception = assertThrows(IllegalStateException.class, () -> {
            vehicleService.checkInVehicle("PARK001", "XYZ-5678");
        });

        assertEquals(VEHICLE_ALREADY_PARKED, exception.getMessage());
    }

    @Test
    void checkOutVehicle_Success() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(1);

        // Mock vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");
        vehicle.setParkingLot(parkingLot);

        // Mock repo response
        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById("ABC-1234")).thenReturn(Optional.of(vehicle));

        // check-out vehicle
        String result = vehicleService.checkOutVehicle("ABC-1234");

        assertEquals(VEHICLE_CHECKED_OUT, result);
        assertEquals(0, parkingLot.getOccupiedSpaces());
    }

    @Test
    void checkOutVehicle_VehicleNotParked_ShouldThrowException() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(0);

        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findById("XYZ-5678")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.checkOutVehicle("XYZ-5678");
        });

        assertEquals(VEHICLE_NOT_PARKED, exception.getMessage());
    }

    @Test
    void getVehiclesByParkingLot_ShouldReturnVehicleList() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(1);

        // Mock vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setLicensePlate(vehicle.getLicensePlate());
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setOwnerName(vehicle.getOwnerName());

        ParkingLotVehiclesDto parkingLotVehiclesDto = new ParkingLotVehiclesDto("PARK001", List.of(vehicleDto));

        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));
        when(vehicleRepository.findByParkingLot(parkingLot)).thenReturn(List.of(vehicle));

        List<ParkingLotVehiclesDto> result = vehicleService.getVehiclesByParkingLot("PARK001");

        assertEquals(1, result.size());
        assertEquals("PARK001", result.get(0).getParkingLot());
        assertEquals(1, result.get(0).getVehicles().size());
        assertEquals("ABC-1234", result.get(0).getVehicles().get(0).getLicensePlate());
        assertEquals(VehicleType.CAR, result.get(0).getVehicles().get(0).getType());
        assertEquals("John Doe", result.get(0).getVehicles().get(0).getOwnerName());
    }

}
