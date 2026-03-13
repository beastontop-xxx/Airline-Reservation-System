package com.airline.model;

import java.sql.Date;

/**
 * POJO representing a row in the `bookings` table,
 * enriched with flight details for display purposes.
 */
public class Booking {

    private int    id;
    private int    userId;
    private int    flightId;
    private Date   bookingDate;
    private String status;          // "Confirmed" | "Cancelled"

    // Joined flight details (populated by BookingDAO)
    private String airline;
    private String source;
    private String destination;
    private Date   flightDate;

    public Booking() {}

    // ── Getters & Setters ──────────────────────────────────────
    public int    getId()        { return id; }
    public void   setId(int id)  { this.id = id; }

    public int    getUserId()            { return userId; }
    public void   setUserId(int userId)  { this.userId = userId; }

    public int    getFlightId()              { return flightId; }
    public void   setFlightId(int flightId)  { this.flightId = flightId; }

    public Date   getBookingDate()                { return bookingDate; }
    public void   setBookingDate(Date bookingDate){ this.bookingDate = bookingDate; }

    public String getStatus()               { return status; }
    public void   setStatus(String status)  { this.status = status; }

    public String getAirline()                { return airline; }
    public void   setAirline(String airline)  { this.airline = airline; }

    public String getSource()               { return source; }
    public void   setSource(String source)  { this.source = source; }

    public String getDestination()                    { return destination; }
    public void   setDestination(String destination)  { this.destination = destination; }

    public Date   getFlightDate()                  { return flightDate; }
    public void   setFlightDate(Date flightDate)   { this.flightDate = flightDate; }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", flightId=" + flightId +
               ", status='" + status + "', bookingDate=" + bookingDate + "}";
    }
}
