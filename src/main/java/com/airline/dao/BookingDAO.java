package com.airline.dao;

import com.airline.model.Booking;
import com.airline.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the `bookings` table.
 */
public class BookingDAO {

    /**
     * Creates a new confirmed booking in the database.
     *
     * @param booking the Booking to persist (userId and flightId must be set)
     * @return true if the insert was successful
     */
    public boolean addBooking(Booking booking) {
        final String SQL =
            "INSERT INTO bookings (user_id, flight_id, booking_date, status) " +
            "VALUES (?, ?, CURDATE(), 'Confirmed')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getFlightId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns all bookings (with joined flight details) for the given user.
     *
     * @param userId the logged-in user's id
     * @return list of enriched Booking objects
     */
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        final String SQL =
            "SELECT b.id, b.user_id, b.flight_id, b.booking_date, b.status, " +
            "       f.airline, f.source, f.destination, f.date AS flight_date " +
            "FROM bookings b " +
            "JOIN flights f ON b.flight_id = f.id " +
            "WHERE b.user_id = ? " +
            "ORDER BY b.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Updates the status of a booking to 'Cancelled'.
     * Also restores the seat count on the corresponding flight.
     *
     * @param bookingId the booking to cancel
     * @param userId    safeguard – only cancel if booking belongs to this user
     * @return true on success
     */
    public boolean cancelBooking(int bookingId, int userId) {
        final String CANCEL_SQL =
            "UPDATE bookings SET status = 'Cancelled' " +
            "WHERE id = ? AND user_id = ? AND status = 'Confirmed'";
        final String RESTORE_SQL =
            "UPDATE flights f " +
            "JOIN bookings b ON f.id = b.flight_id " +
            "SET f.seats = f.seats + 1 " +
            "WHERE b.id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Cancel the booking
            try (PreparedStatement ps1 = con.prepareStatement(CANCEL_SQL)) {
                ps1.setInt(1, bookingId);
                ps1.setInt(2, userId);
                int rows = ps1.executeUpdate();
                if (rows == 0) {
                    con.rollback();
                    return false;   // booking not found or already cancelled
                }
            }

            // 2. Restore the seat
            try (PreparedStatement ps2 = con.prepareStatement(RESTORE_SQL)) {
                ps2.setInt(1, bookingId);
                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // ── private helpers ────────────────────────────────────────

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setFlightId(rs.getInt("flight_id"));
        b.setBookingDate(rs.getDate("booking_date"));
        b.setStatus(rs.getString("status"));
        b.setAirline(rs.getString("airline"));
        b.setSource(rs.getString("source"));
        b.setDestination(rs.getString("destination"));
        b.setFlightDate(rs.getDate("flight_date"));
        return b;
    }
}
