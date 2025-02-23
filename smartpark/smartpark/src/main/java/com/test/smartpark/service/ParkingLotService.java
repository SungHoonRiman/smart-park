package com.test.smartpark.service;

import com.test.smartpark.dto.ParkingLotDto;
import com.test.smartpark.entity.ParkingLot;
import com.test.smartpark.mapper.ParkingLotMapper;
import com.test.smartpark.repository.ParkingLotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.test.smartpark.utility.MessageConstants.*;

@Service
@AllArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotDto registerParkingLot(ParkingLotDto dto) {
        Optional<ParkingLot> existingLot = parkingLotRepository.findById(dto.getLotId());

        if (parkingLotRepository.existsByLocation(dto.getLocation())) {
            throw new IllegalArgumentException(LOCATION_EXISTS);
        } else if (existingLot.isPresent()) {
            throw new IllegalArgumentException(LOT_ID_EXISTS);
        }
        ParkingLot lot = ParkingLotMapper.toEntity(dto);
        ParkingLot savedLot = parkingLotRepository.save(lot);
        return ParkingLotMapper.toDto(savedLot);
    }

    public ParkingLotDto getParkingLot(String lotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(lotId).orElseThrow(() -> new EntityNotFoundException(PARKING_LOT_NOT_FOUND));
        return ParkingLotMapper.toDto(parkingLot);
    }

}
