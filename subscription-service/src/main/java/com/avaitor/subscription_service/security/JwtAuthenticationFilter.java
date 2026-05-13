package com.avaitor.subscription_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      final String authHeader = request.getHeader("Authorization");

      if(authHeader == null || !authHeader.startsWith("Bearer ")){
          filterChain.doFilter(request,response);
          return;
      }

      final String jwt = authHeader.substring(7);
      try{
          String userIdStr = jwtService.extractClaims(jwt, (claims -> claims.get("userId", String.class)));
          String userRole = jwtService.extractClaims(jwt, (claims -> claims.get("role", String.class)));

          if(userIdStr == null || SecurityContextHolder.getContext().getAuthentication() == null){
              UUID userId = UUID.fromString(userIdStr);

              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                      userId,
                      null,
                      List.of(new SimpleGrantedAuthority(userRole))
              );

          }
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
      filterChain.doFilter(request, response);
    }
}
