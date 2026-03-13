<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login – SkyBook</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ── Navbar ────────────────────────────────────────────────── -->
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/jsp/login.jsp">Sky<span>Book</span></a>
    <nav>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp">Login</a>
        <a href="${pageContext.request.contextPath}/jsp/register.jsp">Register</a>
    </nav>
</div>

<!-- ── Content ───────────────────────────────────────────────── -->
<div class="page-wrapper" style="max-width:460px;">
    <div class="card">
        <h2>✈ Welcome Back</h2>

        <%-- Success message after registration --%>
        <% if ("true".equals(request.getParameter("registered"))) { %>
            <div class="alert alert-success">Registration successful! Please log in.</div>
        <% } %>

        <%-- Logout confirmation --%>
        <% if ("true".equals(request.getParameter("logout"))) { %>
            <div class="alert alert-info">You have been logged out safely.</div>
        <% } %>

        <%-- Login error --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="john@example.com" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Your password" required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;">Login</button>
        </form>

        <div class="link-row" style="text-align:center;">
            New here?
            <a href="${pageContext.request.contextPath}/jsp/register.jsp">Create an account</a>
        </div>
    </div>
</div>

</body>
</html>
