<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register – SkyBook</title>
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
<div class="page-wrapper" style="max-width:480px;">
    <div class="card">
        <h2>✈ Create an Account</h2>

        <%-- Error message --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" placeholder="John Doe" required
                       value="${param.name}">
            </div>
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="john@example.com" required
                       value="${param.email}">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Min 6 characters" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;">Register</button>
        </form>

        <div class="link-row" style="text-align:center;">
            Already have an account?
            <a href="${pageContext.request.contextPath}/jsp/login.jsp">Login here</a>
        </div>
    </div>
</div>

</body>
</html>
