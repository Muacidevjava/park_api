package com.muacidev.demoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUltils
{
    public static final String JWT_BEARER = "Bearer ";

    public static final String JWT_Authorization = "Authorization";

    public static final String SECRET_kEY = "012345678-012345679-0123456789";

    public static final long EXPIRE_DAY = 0;

    public static final long EXPIRE_HOUR = 0;

    public static final long EXPIRE_MINUTE = 2;

    private JwtUltils()
    {

    }
    private static Key generateKey(){
       return Keys.hmacShaKeyFor(SECRET_kEY.getBytes(StandardCharsets.UTF_8));
    }
    private static Date toExpireDate(Date start){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAY).plusHours(EXPIRE_HOUR).plusMinutes(EXPIRE_MINUTE);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static JwtToken createToken(String username, String role){
        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);


        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(issueAt)
                .setExpiration(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }
    private static Claims getClaimsFomToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();

        }
        catch (JwtException ex){
            log.error(String.format("Token invalido %s", ex.getMessage()));

        }
        return null;
    }

    public static String getUsernameFromToken(String token){
        return getClaimsFomToken(token).getSubject();
    }


    public static boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token));
         return true;
        }
        catch (JwtException ex){
            log.error(String.format("Token invalido %s", ex.getMessage()));

        }
        return false;
    }


    private static String refactorToken(String token){
        if (token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

}