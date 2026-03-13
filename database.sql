-- ============================================================
--  AIRLINE RESERVATION SYSTEM - DATABASE SCRIPT
--  Run this in MySQL Workbench or via: mysql -u root -p < database.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS airline_db;
USE airline_db;

-- ---------------------------------------------------------------
-- TABLE: users
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id       INT          AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ---------------------------------------------------------------
-- TABLE: flights
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS flights (
    id          INT           AUTO_INCREMENT PRIMARY KEY,
    airline     VARCHAR(100)  NOT NULL,
    source      VARCHAR(100)  NOT NULL,
    destination VARCHAR(100)  NOT NULL,
    date        DATE          NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    seats       INT           NOT NULL DEFAULT 0
);

-- ---------------------------------------------------------------
-- TABLE: bookings
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS bookings (
    id           INT         AUTO_INCREMENT PRIMARY KEY,
    user_id      INT         NOT NULL,
    flight_id    INT         NOT NULL,
    booking_date DATE        NOT NULL,
    status       ENUM('Confirmed','Cancelled') NOT NULL DEFAULT 'Confirmed',
    CONSTRAINT fk_booking_user   FOREIGN KEY (user_id)   REFERENCES users(id)   ON DELETE CASCADE,
    CONSTRAINT fk_booking_flight FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- SAMPLE FLIGHTS  (adjust dates to future dates as needed)
-- ---------------------------------------------------------------
INSERT INTO flights (airline, source, destination, date, price, seats) VALUES
('IndiGo',          'Delhi',     'Mumbai',    '2025-08-10', 3500.00, 50),
('Air India',       'Delhi',     'Mumbai',    '2025-08-10', 4200.00, 30),
('SpiceJet',        'Mumbai',    'Bangalore', '2025-08-11', 2800.00, 40),
('IndiGo',          'Mumbai',    'Bangalore', '2025-08-11', 3100.00, 60),
('Vistara',         'Bangalore', 'Chennai',   '2025-08-12', 2200.00, 45),
('Air India',       'Chennai',   'Kolkata',   '2025-08-13', 5000.00, 20),
('IndiGo',          'Kolkata',   'Delhi',     '2025-08-14', 3800.00, 55),
('GoAir',           'Delhi',     'Hyderabad', '2025-08-15', 3300.00, 35),
('SpiceJet',        'Hyderabad', 'Mumbai',    '2025-08-16', 2900.00, 48),
('Vistara',         'Mumbai',    'Delhi',     '2025-08-17', 4500.00, 25);

-- ---------------------------------------------------------------
-- Verify
-- ---------------------------------------------------------------
SELECT * FROM flights;
