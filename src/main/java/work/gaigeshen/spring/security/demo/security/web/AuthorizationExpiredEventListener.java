package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.context.ApplicationListener;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

/**
 *
 * @author gaigeshen
 */
public class AuthorizationExpiredEventListener implements ApplicationListener<AuthorizationExpiredEvent> {

    private final AccessTokenCreator accessTokenCreator;

    public AuthorizationExpiredEventListener(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    public void onApplicationEvent(AuthorizationExpiredEvent event) {
        accessTokenCreator.invalidate(event.getAuthorization());
    }
}
