package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 *
 * @author gaigeshen
 */
public abstract class AuthenticationToken extends AbstractAuthenticationToken {

    public AuthenticationToken() {
        super(Collections.emptyList());
    }

    @Override
    public final void setAuthenticated(boolean authenticated) {
    }

    @Override
    public final Object getCredentials() {
        return null;
    }

    @Override
    public final Object getPrincipal() {
        return null;
    }
}
