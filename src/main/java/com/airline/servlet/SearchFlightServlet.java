package com.airline.servlet;

import com.airline.dao.FlightDAO;
import com.airline.model.Flight;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Searches for available flights and forwards results to results.jsp (POST /searchFlight).
 */
@WebServlet("/searchFlight")
public class SearchFlightServlet extends HttpServlet {

    private final FlightDAO flightDAO = new FlightDAO();

    /** Show the blank search form. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/search.jsp").forward(req, resp);
    }

    /** Execute the search and forward to results page. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Session guard
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String source      = req.getParameter("source").trim();
        String destination = req.getParameter("destination").trim();
        String date        = req.getParameter("date").trim();

        if (source.isEmpty() || destination.isEmpty() || date.isEmpty()) {
            req.setAttribute("error", "Please fill in all search fields.");
            req.getRequestDispatcher("/jsp/search.jsp").forward(req, resp);
            return;
        }

        List<Flight> flights = flightDAO.searchFlights(source, destination, date);

        req.setAttribute("flights",     flights);
        req.setAttribute("source",      source);
        req.setAttribute("destination", destination);
        req.setAttribute("date",        date);

        req.getRequestDispatcher("/jsp/results.jsp").forward(req, resp);
    }
}
