<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.airline.model.User, com.airline.model.Booking, java.util.List" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");

    // Flash messages from session (set by BookFlightServlet / CancelBookingServlet)
    String successMsg = (String) session.getAttribute("bookingSuccess");
    String errorMsg   = (String) session.getAttribute("bookingError");
    session.removeAttribute("bookingSuccess");
    session.removeAttribute("bookingError");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings – FlyNow</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ── Navbar ────────────────────────────────────────────────── -->
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/jsp/search.jsp">Fly<span>Now</span></a>
    <nav>
        <a href="${pageContext.request.contextPath}/jsp/search.jsp">Search Flights</a>
        <a href="${pageContext.request.contextPath}/bookings">My Bookings</a>
        <a href="${pageContext.request.contextPath}/logout">Logout (<%=loggedUser.getName()%>)</a>
    </nav>
</div>

<!-- ── Content ───────────────────────────────────────────────── -->
<div class="page-wrapper">

    <div class="page-title">
        <h1>My Bookings</h1>
        <p>All reservations made by <strong><%=loggedUser.getName()%></strong>.</p>
    </div>

    <!-- Flash messages -->
    <% if (successMsg != null) { %>
        <div class="alert alert-success">✔ <%=successMsg%></div>
    <% } %>
    <% if (errorMsg != null) { %>
        <div class="alert alert-error">✘ <%=errorMsg%></div>
    <% } %>

    <% if (bookings == null || bookings.isEmpty()) { %>
        <div class="alert alert-info">
            You have no bookings yet.
            <a href="${pageContext.request.contextPath}/jsp/search.jsp">Search for flights now!</a>
        </div>
    <% } else { %>
        <div class="card" style="padding:0; overflow:hidden;">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Airline</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Flight Date</th>
                            <th>Booked On</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Booking b : bookings) { %>
                        <tr>
                            <td><strong>#<%=b.getId()%></strong></td>
                            <td><%=b.getAirline()%></td>
                            <td><%=b.getSource()%></td>
                            <td><%=b.getDestination()%></td>
                            <td><%=b.getFlightDate()%></td>
                            <td><%=b.getBookingDate()%></td>
                            <td>
                                <% if ("Confirmed".equals(b.getStatus())) { %>
                                    <span class="badge badge-confirmed">✔ Confirmed</span>
                                <% } else { %>
                                    <span class="badge badge-cancelled">✘ Cancelled</span>
                                <% } %>
                            </td>
                            <td>
                                <% if ("Confirmed".equals(b.getStatus())) { %>
                                    <a href="${pageContext.request.contextPath}/cancelBooking?bookingId=<%=b.getId()%>"
                                       class="btn btn-danger"
                                       onclick="return confirm('Cancel booking #<%=b.getId()%>? This cannot be undone.');">
                                       Cancel
                                    </a>
                                <% } else { %>
                                    <span style="color:#aaa; font-size:.85rem;">—</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <p style="margin-top:1rem; font-size:.88rem; color:#777;">
            Total: <%=bookings.size()%> booking(s).
        </p>
    <% } %>

    <div style="margin-top:1.5rem;">
        <a href="${pageContext.request.contextPath}/jsp/search.jsp" class="btn btn-primary">+ Book Another Flight</a>
    </div>
</div>

</body>
</html>
