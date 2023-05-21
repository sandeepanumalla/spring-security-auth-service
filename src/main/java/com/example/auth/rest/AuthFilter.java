//package com.example.auth.rest;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Base64;
//import java.util.Objects;
//
//@Component
//public class AuthFilter extends OncePerRequestFilter {
//    private final String USERNAME = "admin";
//    private final String PASSWORD = "strong_password";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
//        String AuthHeader = req.getHeader("Authorization");
//        HttpSession httpSession = req.getSession();
//        System.out.println("sess " + httpSession);
//
//        if(AuthHeader != null && AuthHeader.startsWith("Basic")) {
//            String credentials = new String(Base64.getDecoder().decode(AuthHeader.substring(6)));
//            String[] parts = credentials.split(":");
//            String username = parts[0];
//            String password = parts[1];
//            Object userSession = httpSession.getAttribute("auth");
//            if(userSession != null && userSession.equals(username)) {
//                System.out.println("already logged in");
//                long lastAccessedTime = httpSession.getLastAccessedTime();
//                long currentTime = System.currentTimeMillis();
//
//                if (currentTime - lastAccessedTime <= 60 * 1000) {
//                    // Allow the user to access the API within 60 seconds of their last access
//                    filterChain.doFilter(req, res);
//                }
//            } else if (username.equals(USERNAME) && password.equals(PASSWORD)) {
//                filterChain.doFilter(req, res);
//                httpSession.setAttribute("auth", username);
//                httpSession.setMaxInactiveInterval(60);
//            } else {
//                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        } else {
//            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//    }
//}
