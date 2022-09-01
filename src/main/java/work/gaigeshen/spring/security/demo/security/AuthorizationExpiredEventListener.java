package work.gaigeshen.spring.security.demo.security;

import org.springframework.context.ApplicationListener;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import java.util.Objects;

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
        Authorization authorization = event.getAuthorization();
        if (Objects.isNull(authorization)) {
            return;
        }
        accessTokenCreator.invalidate(authorization);
    }
}
