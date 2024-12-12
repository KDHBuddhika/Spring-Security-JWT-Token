package com.Springboot.security.jwt.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private  static final String SECRET_KEY = "12345678";
    private static final int TOKEN_VALIDITY = 3600*5;     //VALID for 5 hours


    // 1 This method is ,when sent token with request from frontend to backend, identify username of token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);    // subject walata set karanne , username wage dewal JWT token playload ekhta adlawa
    }

    // 2  this method is used for get all claims(details) of the token
    public <T> T getClaimFromToken(String token , Function<Claims,T> claimsResolver){       // <T> T  this return type is genetica, that not include spesific return type
         final Claims claims = getAllClaimsFromToken(token);
         return claimsResolver.apply(claims);
    }

    // 3 this method is used for get all claims(details) of the token
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();             // jwt token ekh playload(Data) ekh thama body ekh kiyanne
    }


    // (A) token ekhn ekh ena user name ekhi , thina user name ekhi samanade kiyala balanawa
    public  Boolean validateToken(String token , UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())  && !isTokenExpired(token));    // token ekh expirde kiylath balanna one
    }


   // (B)  find that token is expired
    public Boolean isTokenExpired(String token){
        final Date expiration = getClaimFromToken(token ,Claims :: getExpiration);
        return expiration.before(new Date());
    }



    // this is method that use to cerate token
    public String createToken(UserDetails userDetails){
      Map<String,Object> claims = new HashMap<>();

      return Jwts.builder()
              .setClaims(claims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
              .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
              .compact();
    }

}
