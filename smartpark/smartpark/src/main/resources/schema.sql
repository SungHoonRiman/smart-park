-- Drop tables if they exist
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS parking_lot;

-- Create Parking Lot Table
CREATE TABLE parking_lot (
    lot_id VARCHAR(50) PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0),
    occupied_spaces INT NOT NULL CHECK (occupied_spaces >= 0)
);

-- Create Vehicle Table
CREATE TABLE vehicle (
    license_plate VARCHAR(20) PRIMARY KEY,
    type VARCHAR(20) CHECK (type IN ('CAR', 'MOTORCYCLE', 'TRUCK')),
    owner_name VARCHAR(100) NOT NULL CHECK (owner_name ~ '^[A-Za-z ]+$'),
    parking_lot_id VARCHAR(50),
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lot(lot_id) ON DELETE SET NULL
);
