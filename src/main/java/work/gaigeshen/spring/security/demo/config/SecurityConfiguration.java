package work.gaigeshen.spring.security.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
import work.gaigeshen.spring.security.demo.security.AbstractAuthenticationProvider;
import work.gaigeshen.spring.security.demo.security.AuthorizationExpiredEventListener;
import work.gaigeshen.spring.security.demo.security.DefaultAuthenticationProvider;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.accesstoken.DefaultAccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.AbstractAccessDeniedHandler;
import work.gaigeshen.spring.security.demo.security.web.DefaultAccessDeniedHandler;
import work.gaigeshen.spring.security.demo.security.web.authentication.AbstractAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.authentication.AccessTokenAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.authentication.DefaultAuthenticationFilter;
import work.gaigeshen.spring.security.demo.security.web.logout.AbstractLogoutHandler;
import work.gaigeshen.spring.security.demo.security.web.logout.DefaultAccessTokenLogoutHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 安全配置
 *
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
        if (authenticationProviders.isEmpty()) {
            return new ProviderManager(new DefaultAuthenticationProvider());
        }
        return new ProviderManager(new ArrayList<>(authenticationProviders));
    }

    @Bean
    public AuthorizationExpiredEventListener authorizationExpiredEventListener() {
        return new AuthorizationExpiredEventListener(accessTokenCreator());
    }

    @Bean
    public AccessTokenAuthenticationFilter accessTokenAuthenticationFilter() {
        return new AccessTokenAuthenticationFilter(accessTokenCreator());
    }

    @ConditionalOnMissingBean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessTokenCreator accessTokenCreator() {
        return DefaultAccessTokenCreator.create();
    }

    @ConditionalOnMissingBean
    @Bean
    public AbstractAccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @ConditionalOnMissingBean
    @Bean
    public AbstractAuthenticationFilter authenticationFilter() {
        return new DefaultAuthenticationFilter(authenticationManager(), accessTokenCreator());
    }

    @ConditionalOnMissingBean
    @Bean
    public AbstractLogoutHandler logoutHandler() {
        return new DefaultAccessTokenLogoutHandler(accessTokenCreator());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationManager(authenticationManager());

        AbstractAuthenticationFilter authenticationFilter = authenticationFilter();
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(accessTokenAuthenticationFilter(), authenticationFilter.getClass());

        AbstractAccessDeniedHandler accessDeniedHandler = accessDeniedHandler();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(accessDeniedHandler);

        AbstractLogoutHandler logoutHandler = logoutHandler();
        http.logout().addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutHandler);

        http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable().cors().configurationSource(r -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.setAllowedMethods(Arrays.asList("GET", "POST"));
            config.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
            config.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
            config.setExposedHeaders(Collections.singletonList("X-Auth-Token"));
            config.setMaxAge(Duration.ofMinutes(30));
            return config;
        });
        return http.build();
    }
}
