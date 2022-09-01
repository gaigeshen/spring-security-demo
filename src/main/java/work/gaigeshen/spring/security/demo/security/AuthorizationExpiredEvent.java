package work.gaigeshen.spring.security.demo.security;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author gaigeshen
 */
public class AuthorizationExpiredEvent extends ApplicationEvent {

    private final Authorization authorization;

    public AuthorizationExpiredEvent(Object source, Authorization authorization) {
        super(source);
        this.authorization = authorization;
    }

    public Authorization getAuthorization() {
        return authorization;
    }
}
