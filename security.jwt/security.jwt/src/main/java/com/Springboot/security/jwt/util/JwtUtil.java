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


    //token validate ,, token thina  user name ekhi user ge user name ekhi samanade kiyala balanwa
    public  Boolean validateToken(String token , UserDetails userDetails){
        final String username = getUsernameFromToken(token);

//        if(username.equals(userDetails.getUsername())){
//            return true;
//        }else {
//            return false;
//        }

        return (username.equals(userDetails.getUsername())  && !isTokenExpired(token));    // token ekh expirde kiylath balanna one
    }


    //token ekh expirde kiylath balana method ekh
    public Boolean isTokenExpired(String token){
        final Date expiration = getClaimFromToken(token ,Claims :: getExpiration);
        return expiration.before(new Date());

    }


    // token ekk genarate kirima
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
