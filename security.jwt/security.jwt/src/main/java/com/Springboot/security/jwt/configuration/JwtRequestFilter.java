package com.Springboot.security.jwt.configuration;


import com.Springboot.security.jwt.service.JwtService;
import com.Springboot.security.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;
    // java ee wala mathakada , ena request wala hama deeyakma meken alla ganna puluwan( header , body ..), token ekh thiyenne header ekh
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         final String requestTokenHeader = request.getHeader("Authorization");        //post ment ekh header wlat gihin balanna puluwan, header wala, autherization kalle thmai token ekh enne, ema token ekhta issarahen thinwa "bearer " kiyala kallak, ekh nathi karala gannth one , esadaaha pallehen variable ekk hadala atha

        String username= null;
        String jwtToken= null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")){
            jwtToken = requestTokenHeader.substring(7);  // palaweni letter 7 remover karanwa

            try{
                username = jwtUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                System.out.println("unable to get jwt token");
            }catch (ExpiredJwtException e){
                System.out.println("Jwt token is expired");
            }

        }else {
            System.out.println("not start with beare");
        }

    // securit context ekh e userta adala authentication welde kiyala balanwa
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = jwtService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken,userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }



    }

}
