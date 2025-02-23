package com.test.smartpark.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.smartpark.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Invalid license plate format")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain only letters and spaces")
    private String ownerName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "lotId")
    private ParkingLot parkingLot;
}
