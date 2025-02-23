package com.test.smartpark.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ParkingLotDto {

    @Id
    @Size(max = 50)
    private String lotId;

    private String location;

    private int capacity;

    private int occupiedSpaces = 0;

}
