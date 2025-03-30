package com.todoProject.ToDoProject.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTGenerationFilter extends OncePerRequestFilter {

    public static String secretKey="djflg;sdfj874w564jkl;gse458nslk;grejgk";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null){
            SecretKey sc1= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            if(sc1!=null){
                String Jwt= Jwts.builder()
                        .issuer("Todo List")
                        .subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities",authentication.getAuthorities().stream().
                                map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 30000000))
                        .signWith(sc1)
                        .compact();

                response.setHeader("Authorization",Jwt);
            }


        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/apiLogin");
    }
}
