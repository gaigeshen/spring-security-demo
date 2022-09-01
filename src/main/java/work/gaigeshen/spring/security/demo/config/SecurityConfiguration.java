package work.gaigeshen.spring.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import work.gaigeshen.spring.security.demo.security.AuthorizationExpiredEventListener;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.accesstoken.DefaultAccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.authentication.AbstractAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.authentication.AbstractAuthenticationProvider;
import work.gaigeshen.spring.security.demo.security.web.authentication.AccessTokenAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.logout.AbstractLogoutHandler;
import work.gaigeshen.spring.security.demo.security.web.logout.DefaultAccessTokenLogoutHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author gaigeshen
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final List<AbstractAuthenticationProvider> authenticationProviders;

    public SecurityConfiguration(List<AbstractAuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new ArrayList<>(authenticationProviders));
    }

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
    public AccessTokenAuthenticationFilter accessTokenAuthenticationFilter() {
        return new AccessTokenAuthenticationFilter(accessTokenCreator());
    }

    @Bean
    public AbstractLogoutHandler logoutHandler() {
        return DefaultAccessTokenLogoutHandler.create(accessTokenCreator());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AbstractAuthenticationFilter authenticationFilter) throws Exception {
        http.authenticationManager(authenticationManager())
                        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
        http.logout().addLogoutHandler(logoutHandler()).logoutSuccessHandler(logoutHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
