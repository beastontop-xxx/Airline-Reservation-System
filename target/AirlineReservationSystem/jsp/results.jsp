<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.airline.model.User, com.airline.model.Flight, java.util.List" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    String source      = (String) request.getAttribute("source");
    String destination = (String) request.getAttribute("destination");
    String date        = (String) request.getAttribute("date");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flight Results – SkyBook</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ── Navbar ────────────────────────────────────────────────── -->
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/jsp/search.jsp">Sky<span>Book</span></a>
    <nav>
        <a href="${pageContext.request.contextPath}/jsp/search.jsp">Search Flights</a>
        <a href="${pageContext.request.contextPath}/bookings">My Bookings</a>
        <a href="${pageContext.request.contextPath}/logout">Logout (<%=loggedUser.getName()%>)</a>
    </nav>
</div>

<!-- ── Content ───────────────────────────────────────────────── -->
<div class="page-wrapper">

    <div class="page-title">
        <h1>Available Flights</h1>
        <p>
            <strong><%=source%></strong> → <strong><%=destination%></strong>
            &nbsp;|&nbsp; Date: <strong><%=date%></strong>
        </p>
    </div>

    <% if (flights == null || flights.isEmpty()) { %>
        <div class="alert alert-info">
            No flights found for this route and date.
            <a href="${pageContext.request.contextPath}/jsp/search.jsp">Try another search.</a>
        </div>
    <% } else { %>
        <div class="card" style="padding:0; overflow:hidden;">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Airline</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Date</th>
                            <th>Price (₹)</th>
                            <th>Seats Left</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Flight f : flights) { %>
                        <tr>
                            <td><%=f.getId()%></td>
                            <td><strong><%=f.getAirline()%></strong></td>
                            <td><%=f.getSource()%></td>
                            <td><%=f.getDestination()%></td>
                            <td><%=f.getDate()%></td>
                            <td>₹ <%=f.getPrice()%></td>
                            <td>
                                <% if (f.getSeats() <= 5) { %>
                                    <span style="color:#c62828; font-weight:700;"><%=f.getSeats()%> ⚠</span>
                                <% } else { %>
                                    <%=f.getSeats()%>
                                <% } %>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/bookFlight?flightId=<%=f.getId()%>"
                                   class="btn btn-success"
                                   onclick="return confirm('Confirm booking with <%=f.getAirline()%> for ₹<%=f.getPrice()%>?');">
                                   Book
                                </a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <p style="margin-top:1rem; font-size:.88rem; color:#777;">
            <%=flights.size()%> flight(s) found. Prices are per person (one-way).
        </p>
    <% } %>

    <div style="margin-top:1.5rem;">
        <a href="${pageContext.request.contextPath}/jsp/search.jsp" class="btn btn-secondary">← New Search</a>
    </div>
</div>

</body>
</html>
