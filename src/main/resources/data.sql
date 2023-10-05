
-- Insert record 1
INSERT INTO battery_log (drone_id, battery_capacity, timestamp)
VALUES (4, 80, '2023-10-04 10:00:00');

-- Insert record 2
INSERT INTO battery_log (drone_id, battery_capacity, timestamp)
VALUES (5, 55, '2023-10-04 08:00:00');

INSERT INTO battery_log (drone_id, battery_capacity, timestamp)
VALUES (6, 30, '2023-10-04 08:00:00');

INSERT INTO battery_log (drone_id, battery_capacity, timestamp)
VALUES (4, 60, '2023-10-04 09:00:00');

-- Insert data into the drones table

-- Insert drone 1
INSERT INTO drone (id, serial_number, model, weight_limit, battery_capacity, state)
VALUES (8, 'X108', 'Lightweight', 500.0, 100, 'IDLE');

-- Insert drone 2
INSERT INTO drone (id, serial_number, model, weight_limit, battery_capacity, state)
VALUES (9, 'Y109', 'MiddleWeight', 500.0, 100, 'IDLE');

-- Insert drone 3
INSERT INTO drone (id, serial_number, model, weight_limit, battery_capacity, state)
VALUES (10, 'Y110', 'Heavyweight', 500.0, 90, 'IDLE');

-- Insert data into the medications table

-- Insert medication 1
INSERT INTO medication (name, weight, code, drone_id)
VALUES ('Flagyl', 50.5, 'FG123', 9);


-- Insert medication 2
INSERT INTO medication (name, weight, code, drone_id)
VALUES ('Paracetamol', 20.6, 'PCT123', 8);

--For deletion
-- Delete medication records linked to drone 2
DELETE FROM medication  WHERE drone_id = 102;

-- Delete drone records
DELETE FROM drone WHERE id IN (4, 5, 6);


--For deletion for foreign key constraint
DELETE FROM battery_log WHERE drone_id = 4;
DELETE FROM drone WHERE id = 4;

DELETE FROM medication WHERE drone_id = 4;
DELETE FROM drone WHERE id = 4;


