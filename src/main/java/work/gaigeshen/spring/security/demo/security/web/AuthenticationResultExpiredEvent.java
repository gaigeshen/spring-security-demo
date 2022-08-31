package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.context.ApplicationEvent;
import work.gaigeshen.spring.security.demo.security.UserDescriptor;

/**
 *
 * @author gaigeshen
 */
public class AuthenticationResultExpiredEvent extends ApplicationEvent {

    private final UserDescriptor userDescriptor;

    public AuthenticationResultExpiredEvent(Object source, UserDescriptor userDescriptor) {
        super(source);
        this.userDescriptor = userDescriptor;
    }

    public UserDescriptor getUserDescriptor() {
        return userDescriptor;
    }
}
