package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 *
 * @author gaigeshen
 */
public class AuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    public AuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }

    public AuthenticationToken(Set<GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    public static AuthenticationToken unauthenticated(Object principal, Object credentials) {
        AuthenticationToken authenticationToken = new AuthenticationToken(principal, credentials);
        authenticationToken.setAuthenticated(false);
        return authenticationToken;
    }

    public static AuthenticationToken authenticated(Authorization authorization, Object credentials) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String authority : authorization.getAuthorities()) {
            if (Objects.isNull(authority)) {
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        AuthenticationToken authenticationToken = new AuthenticationToken(authorities, authorization, credentials);
        authenticationToken.setAuthenticated(true);
        return authenticationToken;
    }

    public static AuthenticationToken authenticated(Authorization authorization) {
        return authenticated(authorization, null);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated && Objects.isNull(getAuthorization())) {
            return;
        }
        super.setAuthenticated(authenticated);
    }

    @Override
    public void eraseCredentials() {
        credentials = null;
    }

    public Authorization getAuthorization() {
        if (principal instanceof Authorization) {
            return (Authorization) principal;
        }
        return null;
    }
}
