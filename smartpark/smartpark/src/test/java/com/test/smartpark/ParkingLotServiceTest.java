package com.test.smartpark;

import com.test.smartpark.controller.ParkingLotController;
import com.test.smartpark.dto.ParkingLotDto;
import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.repository.ParkingLotRepository;
import com.test.smartpark.repository.VehicleRepository;
import com.test.smartpark.service.ParkingLotService;
import com.test.smartpark.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.test.smartpark.utility.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @InjectMocks
    private VehicleService vehicleService;

    @InjectMocks
    private ParkingLotService parkingLotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerParkingLot_Success() {
        // Mock request
        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setLotId("PARK004");
        parkingLotDto.setLocation("Condominium Area");
        parkingLotDto.setCapacity(2);

        // Expected entity
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK004");
        parkingLot.setLocation("Condominium Area");
        parkingLot.setCapacity(2);
        parkingLot.setOccupiedSpaces(0);

        // Mock repo
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);

        ParkingLotDto result = parkingLotService.registerParkingLot(parkingLotDto);

        assertEquals("PARK004", result.getLotId());
        assertEquals("Condominium Area", result.getLocation());
        assertEquals(2, result.getCapacity());
        assertEquals(0, result.getOccupiedSpaces());
    }

    @Test
    void registerVehicle_LocationExist_ShouldThrowException() {
        // Mock request
        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setLotId("PARK004");
        parkingLotDto.setLocation("Condominium Area");
        parkingLotDto.setCapacity(2);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK005");
        parkingLot.setLocation("Condominium Area");
        parkingLot.setCapacity(5);
        parkingLot.setOccupiedSpaces(0);

        when(parkingLotRepository.existsByLocation("Condominium Area")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingLotService.registerParkingLot(parkingLotDto);
        });

        assertEquals(LOCATION_EXISTS, exception.getMessage());
    }

    @Test
    void registerVehicle_LotIdExist_ShouldThrowException() {
        // Mock request
        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setLotId("PARK004");
        parkingLotDto.setLocation("Condominium Area");
        parkingLotDto.setCapacity(2);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK004");
        parkingLot.setLocation("Condominium Area");
        parkingLot.setCapacity(2);
        parkingLot.setOccupiedSpaces(0);

        when(parkingLotRepository.findById("PARK004")).thenReturn(Optional.of(parkingLot));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingLotService.registerParkingLot(parkingLotDto);
        });

        assertEquals(LOT_ID_EXISTS, exception.getMessage());
    }

    @Test
    void getParkingLot_Success() {
        // Mock parking lot
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PARK001");
        parkingLot.setLocation("Basement Area");
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpaces(1);


        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setLotId(parkingLot.getLotId());
        parkingLotDto.setLocation(parkingLot.getLocation());
        parkingLotDto.setCapacity(parkingLot.getCapacity());

        parkingLotDto.setOccupiedSpaces(parkingLot.getOccupiedSpaces());

        when(parkingLotRepository.findById("PARK001")).thenReturn(Optional.of(parkingLot));

        ParkingLotDto result = parkingLotService.getParkingLot("PARK001");

        assertEquals("PARK001", result.getLotId());
        assertEquals("Basement Area", result.getLocation());
        assertEquals(10, result.getCapacity());
        assertEquals(1, result.getOccupiedSpaces());
    }

    @Test
    void getParkingLot_NotFound_ShouldThrowException() {
        when(parkingLotRepository.findById("PARK999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            parkingLotService.getParkingLot("PARK999");
        });

        assertEquals(PARKING_LOT_NOT_FOUND, exception.getMessage());
    }

}
