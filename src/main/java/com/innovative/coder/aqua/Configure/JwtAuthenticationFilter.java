package com.innovative.coder.aqua.Configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.innovative.coder.aqua.Model.Login;
import com.innovative.coder.aqua.Model.User;
import com.innovative.coder.aqua.Service.LoginService;
import com.innovative.coder.aqua.applicationData.ApplicationConstants;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private LoginService loginService;

    private final JwtTokenUtils jwtTokenUtil;

    JwtAuthenticationFilter(LoginService loginService, JwtTokenUtils jwtTokenUtil){
        this.loginService = loginService;
        this.jwtTokenUtil = jwtTokenUtil; // Initialize JwtTokenUtils
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        logger.info("Incoming request: " + request.getRequestURI());

        // Check for the presence of the Authorization header
        if (header == null || !header.startsWith(ApplicationConstants.TOKEN_PREFIX)) {
            logger.warn("Couldn't find bearer string, will ignore the header");
            filter.doFilter(request, response); // Proceed without authentication
            return;
        }

        String authToken = header.replace(ApplicationConstants.TOKEN_PREFIX, "").trim();
        logger.info("Extracted authToken: " + authToken);

        try {
            Login login = loginService.findByTokenAndStatus(authToken, ApplicationEnums.LogInStatusEnum.LOGGED_IN.toString());
            Assert.notNull(login, "TOKEN EXPIRED or DELETED");

            User user = login.getUser();
            Assert.notNull(user, "User doesn't exist with provided token");
            Assert.isTrue(user.getIsActive(), "User is Inactive");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                    List.of(new SimpleGrantedAuthority(user.getUserRole())));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error processing authentication: " + e.getMessage());
            sendErrorResponse(response, "Invalid token", HttpStatus.UNAUTHORIZED);
            return;
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            sendErrorResponse(response, "Authentication failed", HttpStatus.UNAUTHORIZED);
            return;
        }

        filter.doFilter(request, response); // Continue with the filter chain
    }

    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ResponseEntity<String> resp = new ResponseEntity<>(message, status);
        String jsonRespString = ow.writeValueAsString(resp);
        response.setStatus(status.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(jsonRespString);
    }
}
