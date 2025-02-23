package com.test.smartpark.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.EntityNotFoundException;

public enum VehicleType {
    CAR, MOTORCYCLE, TRUCK;
    @JsonCreator
    public static VehicleType fromString(String value) {
        for (VehicleType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new EntityNotFoundException("Invalid vehicle type: " + value);
    }
}
