package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author gaigeshen
 */
public class UserDescriptorNotFoundException extends AuthenticationException {

    public UserDescriptorNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserDescriptorNotFoundException(String msg) {
        super(msg);
    }
}
