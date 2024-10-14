package com.innovative.coder.aqua.Configure;

import com.innovative.coder.aqua.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
//    private final String[] PUBLIC_RESOURCE_AND_URL = {"/","/swagger-ui/**", "/v3/api-docs/**", "/", "/index.html", "/views/**", "/css/**", "/libs/**", "/js/**", "/login/**","/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/v2/api-docs", "/configuration/ui", "/webjars/**"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Cors Configuration
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));  // Use List.of() instead of ImmutableList
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.addAllowedHeader("Authorization");
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        httpSecurity.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration(configuration);
            return corsConfig.applyPermitDefaultValues();
        }));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Updated authorizeHttpRequests configuration
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/", "/index.html", "/views/**", "/css/**", "/libs/**", "/js/**", "/user/**","/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/v2/api-docs", "/configuration/ui", "/webjars/**").permitAll()
                .anyRequest().authenticated()
        ).exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedHandler(accessDeniedHandler())
        ).addFilterBefore(new JwtAuthenticationFilter(loginService, jwtTokenUtils), BasicAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(PUBLIC_RESOURCE_AND_URL);
//    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}
