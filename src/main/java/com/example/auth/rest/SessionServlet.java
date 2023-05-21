//package com.example.auth.rest;
//
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//@WebServlet("/session")
//public class SessionServlet extends HttpServlet {
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Get or create the session
//        HttpSession session = request.getSession();
//
//        // Set session attribute
//        session.setAttribute("username", "JohnDoe");
//
//        // Configure session timeout
//        int sessionTimeoutSeconds = (int) TimeUnit.MINUTES.toSeconds(5);
//        session.setMaxInactiveInterval(sessionTimeoutSeconds);
//
//        // Get session attribute
//        String username = (String) session.getAttribute("username");
//
//        // Print the session attribute
//        response.getWriter().println("Username: " + username);
//    }
//}
