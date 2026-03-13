<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.airline.model.User" %>
<%
    // Session guard
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Flights – SkyBook</title>
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
<div class="page-wrapper" style="max-width:580px;">

    <div class="page-title">
        <h1>Search Available Flights</h1>
        <p>Enter your travel details to find the best flights.</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">${error}</div>
    <% } %>

    <div class="card">
        <form action="${pageContext.request.contextPath}/searchFlight" method="post">
            <div class="form-group">
                <label for="source">From (City)</label>
                <input type="text" id="source" name="source" placeholder="e.g. Delhi" required>
            </div>
            <div class="form-group">
                <label for="destination">To (City)</label>
                <input type="text" id="destination" name="destination" placeholder="e.g. Mumbai" required>
            </div>
            <div class="form-group">
                <label for="date">Travel Date</label>
                <input type="date" id="date" name="date" required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;">🔍 Search Flights</button>
        </form>
    </div>

    <!-- Quick city guide -->
    <div class="card" style="margin-top:1.5rem; background:#e3f2fd;">
        <h2 style="font-size:1rem; color:#1565c0; margin-bottom:.6rem;">✈ Available Routes (Sample Data)</h2>
        <table>
            <thead><tr><th>From</th><th>To</th></tr></thead>
            <tbody>
                <tr><td>Delhi</td><td>Mumbai</td></tr>
                <tr><td>Mumbai</td><td>Bangalore</td></tr>
                <tr><td>Bangalore</td><td>Chennai</td></tr>
                <tr><td>Chennai</td><td>Kolkata</td></tr>
                <tr><td>Kolkata</td><td>Delhi</td></tr>
                <tr><td>Delhi</td><td>Hyderabad</td></tr>
                <tr><td>Hyderabad</td><td>Mumbai</td></tr>
                <tr><td>Mumbai</td><td>Delhi</td></tr>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
