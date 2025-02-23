package com.test.smartpark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Id
    @Size(max = 50)
    @Column(unique = true)
    private String lotId;

    private String location;

    private int capacity;

    private int occupiedSpaces = 0;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private List<Vehicle> parkedVehicles = new ArrayList<>();

    public boolean isFull() {
        return occupiedSpaces >= capacity;
    }
}
