package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractAuthenticationProvider<T extends AuthenticationToken> implements AuthenticationProvider {

    private final Class<?> authenticationTokenClass;

    protected AbstractAuthenticationProvider() {
        ParameterizedType genSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        authenticationTokenClass = (Class<?>) genSuperClass.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDescriptor descriptor = authenticate((T) authentication);
        AuthenticationResult result = new AuthenticationResult(descriptor);
        result.eraseCredentials();
        result.setDetails(authentication.getDetails());
        return result;
    }

    protected abstract UserDescriptor authenticate(T token) throws AuthenticationException;

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == authenticationTokenClass;
    }
}
