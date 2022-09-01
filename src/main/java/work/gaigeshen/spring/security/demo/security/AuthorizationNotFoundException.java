package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author gaigeshen
 */
public class AuthorizationNotFoundException extends AuthenticationException {

    public AuthorizationNotFoundException(String msg) {
        super(msg);
    }
}
