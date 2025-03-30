package com.todoProject.ToDoProject.controller;

import com.todoProject.ToDoProject.model.JWTResponseDTO;
import com.todoProject.ToDoProject.model.JwtRequestDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static com.todoProject.ToDoProject.filter.JWTGenerationFilter.secretKey;

@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/apiLogin")
    public ResponseEntity<JWTResponseDTO> apiLogin(JwtRequestDTO jwtRequestDTO){
        Authentication authentication= UsernamePasswordAuthenticationToken.unauthenticated(jwtRequestDTO.getUsername(),
                jwtRequestDTO.getPassword());
        Authentication authenticationResponse=authenticationManager.authenticate(authentication);

        String Jwt="";
        if (authenticationResponse!=null && authenticationResponse.isAuthenticated()){

            SecretKey sc3= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            if (sc3!=null){
                Jwt= Jwts.builder()
                        .issuer("TodoList")
                        .subject("JWT token")
                        .claim("username",authenticationResponse.getName())
                        .claim("authorities",authenticationResponse.getAuthorities().stream().
                                map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis()+30000000))
                        .compact();

            }
        }
        return ResponseEntity.status(HttpStatus.OK).header("Authorization",Jwt).
                body(new JWTResponseDTO(HttpStatus.OK.getReasonPhrase(), Jwt));
    }
}
