package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

private JwtUtils jwtUtils;

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){

        return new CustomAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }


    @Autowired
    CustomUserDetailService customUserDetailService;


    public void configure( AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider( customAuthenticationProvider());
    }





    //fformlogin() .loginpage("/xyz") .permitAll() .....else endless loop

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/processlogin")
                .defaultSuccessUrl("/user/genToken")
                .and()
                .httpBasic()
                .and()
                .addFilterBefore(jwtFilter(),UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

        return (web -> web.ignoring().requestMatchers("/images/**","/js/**"));
    }


}
