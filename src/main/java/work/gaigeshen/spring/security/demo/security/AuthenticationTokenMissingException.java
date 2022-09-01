package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author gaigeshen
 */
public class AuthenticationTokenMissingException extends AuthenticationException {

    public AuthenticationTokenMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationTokenMissingException(String msg) {
        super(msg);
    }
}
