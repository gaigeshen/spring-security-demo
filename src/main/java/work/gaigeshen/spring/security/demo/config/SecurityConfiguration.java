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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import work.gaigeshen.spring.security.demo.security.DefaultAuthenticationProvider;
import work.gaigeshen.spring.security.demo.security.DefaultUserDescriptor;
import work.gaigeshen.spring.security.demo.security.UserDescriptorLoader;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.accesstoken.DefaultAccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.*;
import work.gaigeshen.spring.security.demo.web.JsonAccessTokenAuthenticationResultHandler;
import work.gaigeshen.spring.security.demo.web.JsonAccessTokenLogoutResultHandler;

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
    public AuthenticationResultExpiredEventListener authenticationResultExpiredEventListener() {
        return new AuthenticationResultExpiredEventListener(accessTokenCreator());
    }

    @Bean
    public AuthenticationResultHandler authenticationResultHandler() {
        return new JsonAccessTokenAuthenticationResultHandler(accessTokenCreator());
    }

    @Bean
    public LogoutResultHandler logoutResultHandler() {
        return new JsonAccessTokenLogoutResultHandler(accessTokenCreator());
    }

    @Bean
    public UserDescriptorLoader userDescriptorLoader() {
        return token -> DefaultUserDescriptor.builder()
                .userId("1").username("admin").password(passwordEncoder().encode("123456")).authorities(Collections.emptyList())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new DefaultAuthenticationProvider(userDescriptorLoader(), passwordEncoder()));
    }

    @Bean
    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter(new AntPathRequestMatcher("/login", "POST"), authenticationManager());
        loginFilter.setAuthenticationDetailsSource(new WebParametersAdditionalAuthenticationDetailsSource());
        loginFilter.setAuthenticationSuccessHandler(authenticationResultHandler());
        loginFilter.setAuthenticationFailureHandler(authenticationResultHandler());
        return loginFilter;
    }

    @Bean
    public AccessTokenAuthenticationFilter accessTokenAuthenticationFilter() {
        return new AccessTokenAuthenticationFilter(accessTokenCreator());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationManager(authenticationManager());

        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                        .addFilterBefore(accessTokenAuthenticationFilter(), LoginFilter.class);

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
