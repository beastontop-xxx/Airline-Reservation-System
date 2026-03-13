package com.airline.dao;

import com.airline.model.Flight;
import com.airline.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the `flights` table.
 */
public class FlightDAO {

    /**
     * Searches for available flights matching source, destination, and date.
     *
     * @param source      departure city
     * @param destination arrival city
     * @param date        travel date (yyyy-MM-dd)
     * @return list of matching Flight objects with at least 1 available seat
     */
    public List<Flight> searchFlights(String source, String destination, String date) {
        List<Flight> flights = new ArrayList<>();
        final String SQL =
            "SELECT * FROM flights " +
            "WHERE LOWER(source) = LOWER(?) " +
            "  AND LOWER(destination) = LOWER(?) " +
            "  AND date = ? " +
            "  AND seats > 0 " +
            "ORDER BY price ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, source);
            ps.setString(2, destination);
            ps.setString(3, date);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flights.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    /**
     * Retrieves a single flight by its primary key.
     */
    public Flight getFlightById(int flightId) {
        final String SQL = "SELECT * FROM flights WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrements the seat count by 1 for the given flight.
     * Should be called inside the same transaction as the booking insert (simplified here).
     */
    public boolean deductSeat(int flightId) {
        final String SQL = "UPDATE flights SET seats = seats - 1 WHERE id = ? AND seats > 0";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, flightId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── private helpers ────────────────────────────────────────

    private Flight mapRow(ResultSet rs) throws SQLException {
        Flight f = new Flight();
        f.setId(rs.getInt("id"));
        f.setAirline(rs.getString("airline"));
        f.setSource(rs.getString("source"));
        f.setDestination(rs.getString("destination"));
        f.setDate(rs.getDate("date"));
        f.setPrice(rs.getBigDecimal("price"));
        f.setSeats(rs.getInt("seats"));
        return f;
    }
}
