package ru.nikitung.gateway.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.nikitung.gateway.dto.UserResponse;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    // Generate the secret key from base64 encoded secret
    private SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null; // or handle exception as needed
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        boolean valid = username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        return valid;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            boolean expired = expiration.before(new Date());


            return expired;
        } catch (JwtException e) {
            return true;
        }
    }
}
