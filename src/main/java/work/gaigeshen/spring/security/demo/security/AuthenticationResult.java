package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 *
 * @author gaigeshen
 */
public class AuthenticationResult extends AbstractAuthenticationToken {

    private final UserDescriptor userDescriptor;

    public AuthenticationResult(UserDescriptor userDescriptor) {
        super(userDescriptor.getAuthorities());
        this.userDescriptor = userDescriptor;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDescriptor;
    }

    public UserDescriptor getUserDescriptor() {
        return userDescriptor;
    }
}
