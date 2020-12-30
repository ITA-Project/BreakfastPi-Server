package com.ita.domain.security;

import com.ita.domain.security.handler.CustomAccessDeniedHandler;
import com.ita.domain.security.handler.CustomAuthenticationEntryPoint;
import com.ita.domain.security.handler.SecurityWhitelistHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityWhitelistHandler securityWhitelistHandler;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers(securityWhitelistHandler.getWhiteList())
        .permitAll()
//        .antMatchers("/orders/denytest").hasAnyRole("admin")
//        .antMatchers("/orders/testt", "/orders/denytest").hasAnyRole("user")
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        .accessDeniedHandler(new CustomAccessDeniedHandler());
    http.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter(redisTemplate);
  }

}
