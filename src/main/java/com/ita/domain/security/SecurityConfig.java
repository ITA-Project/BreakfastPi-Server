package com.ita.domain.security;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Order;
import com.ita.domain.security.handler.CustomAccessDeniedHandler;
import com.ita.domain.security.handler.CustomAuthenticationEntryPoint;
import com.ita.domain.security.handler.SecurityWhitelistHandler;
import com.ita.domain.service.OrderService;
import com.ita.domain.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
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
