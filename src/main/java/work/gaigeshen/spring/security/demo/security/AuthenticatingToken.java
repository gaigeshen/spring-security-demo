package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author gaigeshen
 */
public class AuthenticatingToken extends AbstractAuthenticationToken {

    private final Object principal;

    private final Object credentials;

    public AuthenticatingToken(Object principal, Object credentials, Object details) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setDetails(details);
    }

    @Override
    public void setAuthenticated(boolean authenticated) { }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
