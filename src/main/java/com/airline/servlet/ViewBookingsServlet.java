package com.airline.servlet;

import com.airline.dao.BookingDAO;
import com.airline.model.Booking;
import com.airline.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Loads the logged-in user's bookings and forwards to bookings.jsp (GET /bookings).
 */
@WebServlet("/bookings")
public class ViewBookingsServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        List<Booking> bookings = bookingDAO.getBookingsByUser(user.getId());
        req.setAttribute("bookings", bookings);

        req.getRequestDispatcher("/jsp/bookings.jsp").forward(req, resp);
    }
}
