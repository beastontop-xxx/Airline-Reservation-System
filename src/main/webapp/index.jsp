<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // If already logged in, send to search; otherwise go to login
    if (session.getAttribute("loggedUser") != null) {
        response.sendRedirect(request.getContextPath() + "/jsp/search.jsp");
    } else {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }
%>
