package com.airline.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * POJO representing a row in the `flights` table.
 */
public class Flight {

    private int        id;
    private String     airline;
    private String     source;
    private String     destination;
    private Date       date;
    private BigDecimal price;
    private int        seats;

    public Flight() {}

    public Flight(int id, String airline, String source, String destination,
                  Date date, BigDecimal price, int seats) {
        this.id          = id;
        this.airline     = airline;
        this.source      = source;
        this.destination = destination;
        this.date        = date;
        this.price       = price;
        this.seats       = seats;
    }

    // ── Getters & Setters ──────────────────────────────────────
    public int     getId()        { return id; }
    public void    setId(int id)  { this.id = id; }

    public String  getAirline()               { return airline; }
    public void    setAirline(String airline)  { this.airline = airline; }

    public String  getSource()               { return source; }
    public void    setSource(String source)  { this.source = source; }

    public String  getDestination()                    { return destination; }
    public void    setDestination(String destination)  { this.destination = destination; }

    public Date    getDate()             { return date; }
    public void    setDate(Date date)    { this.date = date; }

    public BigDecimal getPrice()                 { return price; }
    public void       setPrice(BigDecimal price) { this.price = price; }

    public int  getSeats()             { return seats; }
    public void setSeats(int seats)    { this.seats = seats; }

    @Override
    public String toString() {
        return "Flight{id=" + id + ", airline='" + airline + "', " +
               source + " -> " + destination + ", date=" + date + ", seats=" + seats + "}";
    }
}
