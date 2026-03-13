package com.airline.servlet;

import com.airline.dao.UserDAO;
import com.airline.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Handles new-user registration (POST /register).
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    /** Show the registration form. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }

    /** Process the submitted registration form. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name     = req.getParameter("name").trim();
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);  // hash with BCrypt in production

        boolean success = userDAO.registerUser(user);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp?registered=true");
        } else {
            req.setAttribute("error", "Registration failed. Email may already be in use.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        }
    }
}
