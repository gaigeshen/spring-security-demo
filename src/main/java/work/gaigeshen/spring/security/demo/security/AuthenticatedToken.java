package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class AuthenticatedToken extends AbstractAuthenticationToken {

    private final Authorization authorization;

    public AuthenticatedToken(Set<GrantedAuthority> grantedAuthorities, Authorization authorization) {
        super(grantedAuthorities);
        setAuthenticated(true);
        this.authorization = authorization;
    }

    public static AuthenticatedToken create(Authorization authorization) {
        if (Objects.isNull(authorization.getAuthorities())) {
            return new AuthenticatedToken(null, authorization);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String authority : authorization.getAuthorities()) {
            if (Objects.isNull(authority)) {
                continue;
            }
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return new AuthenticatedToken(grantedAuthorities, authorization);
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    @Override
    public Object getPrincipal() {
        return authorization;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
