package work.gaigeshen.spring.security.demo.security.web.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;
import work.gaigeshen.spring.security.demo.security.Authorization;
import work.gaigeshen.spring.security.demo.security.AuthorizationNotFoundException;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authorization authorization = authenticate((AuthenticationToken) authentication);
        if (Objects.isNull(authorization)) {
            throw new AuthorizationNotFoundException("authorization of " + authentication + " not found");
        }
        return AuthenticationToken.authenticated(authorization, authentication.getCredentials());
    }

    @Override
    public final boolean supports(Class<?> authentication) {
        return AuthenticationToken.class == authentication;
    }

    protected abstract Authorization authenticate(AuthenticationToken token) throws AuthenticationException;
}
