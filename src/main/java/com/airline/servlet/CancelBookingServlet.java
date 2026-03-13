package com.airline.servlet;

import com.airline.dao.BookingDAO;
import com.airline.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Cancels a confirmed booking owned by the logged-in user (GET /cancelBooking?bookingId=X).
 */
@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ── Session guard ─────────────────────────────────────
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String bookingIdStr = req.getParameter("bookingId");
        if (bookingIdStr == null || bookingIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/bookings");
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/bookings");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        boolean cancelled = bookingDAO.cancelBooking(bookingId, user.getId());

        if (cancelled) {
            session.setAttribute("bookingSuccess", "Booking #" + bookingId + " cancelled successfully.");
        } else {
            session.setAttribute("bookingError", "Cancellation failed. Booking may already be cancelled.");
        }

        resp.sendRedirect(req.getContextPath() + "/bookings");
    }
}
