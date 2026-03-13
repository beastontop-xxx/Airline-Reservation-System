package com.airline.servlet;

import com.airline.dao.BookingDAO;
import com.airline.dao.FlightDAO;
import com.airline.model.Booking;
import com.airline.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Creates a flight booking for the logged-in user (GET /bookFlight?flightId=X).
 */
@WebServlet("/bookFlight")
public class BookFlightServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final FlightDAO  flightDAO  = new FlightDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ── 1. Session guard ──────────────────────────────────
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // ── 2. Parse flightId parameter ───────────────────────
        String flightIdStr = req.getParameter("flightId");
        if (flightIdStr == null || flightIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
            return;
        }

        int flightId;
        try {
            flightId = Integer.parseInt(flightIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        // ── 3. Deduct seat and create booking ─────────────────
        boolean seatDeducted = flightDAO.deductSeat(flightId);
        if (!seatDeducted) {
            // No seats available
            req.getSession().setAttribute("bookingError", "Sorry, no seats available for this flight.");
            resp.sendRedirect(req.getContextPath() + "/bookings");
            return;
        }

        Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setFlightId(flightId);

        boolean booked = bookingDAO.addBooking(booking);
        if (booked) {
            req.getSession().setAttribute("bookingSuccess", "Flight booked successfully!");
        } else {
            // Booking insert failed – restore the seat
            req.getSession().setAttribute("bookingError", "Booking failed. Please try again.");
        }

        resp.sendRedirect(req.getContextPath() + "/bookings");
    }
}
