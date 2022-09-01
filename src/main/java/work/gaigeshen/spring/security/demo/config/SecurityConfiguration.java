package work.gaigeshen.spring.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import work.gaigeshen.spring.security.demo.security.AuthorizationExpiredEventListener;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.accesstoken.DefaultAccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.authentication.AccessTokenAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.authentication.AuthenticationHandler;
import work.gaigeshen.spring.security.demo.security.web.authentication.JsonAccessTokenAuthenticationHandler;
import work.gaigeshen.spring.security.demo.security.web.logout.JsonAccessTokenLogoutHandler;
import work.gaigeshen.spring.security.demo.security.web.logout.LogoutHandler;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author gaigeshen
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public AccessTokenCreator accessTokenCreator() {
        return DefaultAccessTokenCreator.create();
    }

    @Bean
    public AuthorizationExpiredEventListener authorizationExpiredEventListener() {
        return new AuthorizationExpiredEventListener(accessTokenCreator());
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new JsonAccessTokenAuthenticationHandler(accessTokenCreator());
    }

    @Bean
    public LogoutHandler logoutResultHandler() {
        return new JsonAccessTokenLogoutHandler(accessTokenCreator());
    }

    @Bean
    public AccessTokenAuthenticationFilter accessTokenAuthenticationFilter() {
        return new AccessTokenAuthenticationFilter(accessTokenCreator());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
            corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
            corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
            corsConfiguration.setExposedHeaders(Collections.singletonList("X-Auth-Token"));
            corsConfiguration.setMaxAge(Duration.ofMinutes(30));
            return corsConfiguration;
        });
        http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/register").permitAll().anyRequest().authenticated();
        http.logout().addLogoutHandler(logoutResultHandler()).logoutSuccessHandler(logoutResultHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
