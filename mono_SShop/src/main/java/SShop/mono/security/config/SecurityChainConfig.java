package SShop.mono.security.config;

import static SShop.mono.security.Permissions.MANAGER_READ;
import static SShop.mono.security.Permissions.USER_READ;
import static SShop.mono.security.Roles.ADMIN;
import static SShop.mono.security.Roles.MANAGER;
import static SShop.mono.security.Roles.USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private static final String[] WHITE_LIST_URL = {"/api/v1/client/**",
        "/api/v1/product/**",
        "/api/v1/crypto/**",
        "/api/v1/notification/**",
        "/api/v1/cart/**",
        "/api/v1/admin/product/**",
        "/api/v1/user/**",
        "/api/v1/message/**",
        "/ws/**"




    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        return httpSecurity
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(req ->
//                req.requestMatchers(WHITE_LIST_URL)
//                    .permitAll()
//                    .requestMatchers("/api/v1/category/**").hasAnyRole(ADMIN.name())
//                    .requestMatchers(GET, "/api/v1/category/**").hasAnyAuthority( MANAGER_READ.name())
//                     .anyRequest()
//                    .authenticated()
//            )
//            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//            .authenticationProvider(authenticationProvider)
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//            .build();

        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req.anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
