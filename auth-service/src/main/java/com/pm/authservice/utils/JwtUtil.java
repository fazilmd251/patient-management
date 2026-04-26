package com.pm.authservice.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;
    public JwtUtil(
//            @Value("${JWT_SECRET}") String secret
    ){
        String secret="8pY5uX9vK2mN5zQ8wS1bV4xG7yJ0eM3hP6rA9dB2tC5fH8jK1nN4qS7uW0z3p5r8";
    byte[] keyBytes= Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
    this.secretKey= Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email,String role){
    return Jwts.builder()
            .subject(email)
            .claim("role",role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
            .signWith(this.secretKey)
            .compact();
    }
    public void validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
        }catch(SignatureException e){
            throw new JwtException("Invalid Jwt secret key");
        }catch (JwtException e){
            throw new JwtException("Invalid JWT token");
        }
    }
}
