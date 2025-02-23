-- Insert Parking Lots
INSERT INTO parking_lot (lot_id, location, capacity, occupied_spaces) VALUES
('PARK001', 'Basement Area', 5, 0),
('PARK002', 'Mall Area', 10, 0);

-- Insert Vehicles
INSERT INTO vehicle (license_plate, type, owner_name, parking_lot_id) VALUES
('ABC-1234', 'CAR', 'John Gen', NULL),
('XYZ-9876', 'MOTORCYCLE', 'Gwen Stacy', NULL),
('TRK-5555', 'TRUCK', 'Mike Tyson', NULL),
('CAR-9999', 'CAR', 'Alice Hirose', NULL);
