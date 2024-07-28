package com.muacidev.demoparkapi.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter{



    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUltils.JWT_Authorization);
        if (token == null || !token.startsWith(JwtUltils.JWT_BEARER)) {
            log.info("JWT Token está nulo , vazio ou não iniciado com 'Bearer'.");
            filterChain.doFilter(request, response);
            return;
        }

        if(!JwtUltils.isTokenValid(token)){
           log.warn("JWT token está invalido ou expirado");
           filterChain.doFilter(request, response);
           return;
        }

        String username = JwtUltils.getUsernameFromToken(token);

        toAuthentication(request, username);

        filterChain.doFilter(request, response);

    }

    private void toAuthentication(HttpServletRequest request, String username) {
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}