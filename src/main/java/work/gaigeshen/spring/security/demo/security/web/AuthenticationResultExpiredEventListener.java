package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.context.ApplicationListener;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

/**
 *
 * @author gaigeshen
 */
public class AuthenticationResultExpiredEventListener implements ApplicationListener<AuthenticationResultExpiredEvent> {

    private final AccessTokenCreator accessTokenCreator;

    public AuthenticationResultExpiredEventListener(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    public void onApplicationEvent(AuthenticationResultExpiredEvent event) {
    }
}
