package com.test.smartpark.dto;

import com.test.smartpark.enums.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VehicleDto {

    @Id
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Invalid license plate format")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain only letters and spaces")
    private String ownerName;


}
