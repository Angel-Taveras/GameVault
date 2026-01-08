package com.pm.login.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    // Secret en Base64 (debe tener al menos 256 bits para HS256)
    private final String SECRET_KEY = "Y2xhdmVfc3VwZXJfc2VndXJhX2RlX2FsX21lbm9zXzMyX2J5dGVzIQ==";
    
    // Tiempo de expiración: 10 horas
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * Extrae el username (subject) del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico usando una función
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Jwts.parser()
                .setSigningKey(keyBytes)  // ✅ Usando bytes decodificados
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si el token ha expirado
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un nuevo token JWT para el usuario
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Crea el token JWT con claims y subject
     */
    private String createToken(Map<String, Object> claims, String subject) {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);  // ✅ Decodificar aquí también
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, keyBytes)  // ✅ Usar bytes decodificados
                .compact();
    }

    /**
     * Valida el token verificando username y expiración
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException e) {
            System.err.println("Error validando token: " + e.getMessage());
            return false;
        }
    }
}
