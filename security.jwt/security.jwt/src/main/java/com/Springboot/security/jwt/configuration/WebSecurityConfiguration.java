package com.Springboot.security.jwt.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true  )
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/authentication","/user/register-new-user").permitAll()
                .antMatchers(HttpHeaders.ALLOW).permitAll()                           //http header ekh thamai jwt token ekh enne , ethkta eh gnna header acess arla thiyenna one
                .anyRequest().authenticated()               // header ekk nathuwa ekh onema request ekk authentication kala yuthuya
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)    // ihala request walata amatharawa unautharized reqeust ekk enakota ekh handle kirima sadaha meya yodagani
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);      // session id hadanna epa. api ewa manage karanawa                  // web page ekhn cookied id generate karaganna epa , api ekh manage karagannm kiyala thmai kiyanne
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
