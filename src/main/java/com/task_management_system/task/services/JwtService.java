package com.task_management_system.task.services;

import com.task_management_system.task.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;



    public String generateToken(User user) {
        Map<String,Object> claims= new HashMap<>();
        claims.put("role",user.getRole());
        claims.put("phoneNumber",user.getPhoneNumber());
        claims.put("email",user.getEmail());
        claims.put("userName",user.getUserName());
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUserName())
                .issuer("DCR")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .and()
                .signWith(generatekey())
                .compact();
    }

    private SecretKey generatekey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    private String getSecretKey(){
        return secretKey;
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver)  {
        Claims claims=extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(generatekey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
