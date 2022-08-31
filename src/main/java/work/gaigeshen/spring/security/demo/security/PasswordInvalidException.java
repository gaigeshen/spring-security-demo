package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author gaigeshen
 */
public class PasswordInvalidException extends AuthenticationException {

    public PasswordInvalidException(String msg) {
        super(msg);
    }
}
