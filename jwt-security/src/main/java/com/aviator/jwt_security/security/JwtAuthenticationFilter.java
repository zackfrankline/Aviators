package com.aviator.jwt_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//extend OncePerRequestFilter to guarantee this logic runs exactly one time for every single HTTP request.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method intercepts every request.
     * @param request The incoming HTTP request (headers, body, URL).
     * @param response The outgoing HTTP response (we can modify this if needed).
     * @param filterChain The chain of filters. We must call .doFilter() to let the request proceed.
     */
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Fast Fail: If no header or doesn't start with "Bearer ", pass it on.
        // We do NOT return an error here. Why?
        // Because the user might be trying to access a public page (like /login or /register).
        // If we threw an error here, public pages would break.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //3. Extract the token (Remove "Bearer" prefix)
        jwt = authHeader.substring(7);

        //4. extract Username from JWT (Decodes the token)
        try{
            userEmail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request,response);
            return;
        }

        //5. Authentication Process
        // check if we have a userEmail and they are not already authenticated.
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            //6. validate token
            // checks: is username matching? Is Token expired?
            if(jwtService.isTokenValid(jwt,userDetails)){

                //7. create Authentication token
                // this object is what Spring uses to know "who is this?"
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // we Dont store credentials (password) once authentication
                        userDetails.getAuthorities() // Passes roles (ROLE_ADMIN, ROLE_AUDIENCE)
                );


                // add details like IP address and Session ID
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //8. Log the user in
                // this saves the user in the context for this specific Thread/request.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }

        //9. continue the chain
        // pass this request to the next filter ( eg. AuthorizationFilter or the Controller).
        filterChain.doFilter(request,response);
    }
}
