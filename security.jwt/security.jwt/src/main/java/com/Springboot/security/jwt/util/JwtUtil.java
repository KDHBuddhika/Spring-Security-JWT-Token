package com.Springboot.security.jwt.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtUtil {

    private  static final String SECRET_KEY = "12345678";
    private static final int TOKEN_VALIDITY = 3600*5;     //VALID for 5 hours


    //user send karanawa  token ekhth ekka request ekk, ethakota e user kaude kiyala hoyaganimata adala method ekh
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);                //claims is user name , getSubject is sub of token ,u can refer to serch google that jwt
    }

    public <T> T getClaimFromToken(String token , Function<Claims,T> claimsResolver){       //                       // return a generic type in a method.onama wargaye data type ekhkin return karanwa .T (for "Type"), E (for "Element"), K (for "Key"), V (for "Value"),
         final Claims claims = getAllClaimsFromToken(token);
         return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
