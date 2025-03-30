package com.todoProject.ToDoProject.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static com.todoProject.ToDoProject.filter.JWTGenerationFilter.secretKey;

public class JWTValidatorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Jwt=request.getHeader("Authorization");
        SecretKey sc2=Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        if(Jwt!=null && sc2!=null && Jwt.startsWith("Bearer ")){
            Jwt=Jwt.substring(7);
            try {
                Claims claims=Jwts.parser()
                        .verifyWith(sc2)
                        .build()
                        .parseSignedClaims(Jwt)
                        .getPayload();

                String username=String.valueOf(claims.get("username"));
                String authority=String.valueOf(claims.get("authorities"));

                Authentication authentication=new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("Invalid Token received");
            }

        }
        filterChain.doFilter(request,response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/apiLogin");
    }
}
