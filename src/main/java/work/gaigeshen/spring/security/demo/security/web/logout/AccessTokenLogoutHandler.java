package work.gaigeshen.spring.security.demo.security.web.logout;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.authentication.AccessTokenAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gaigeshen
 */
public abstract class AccessTokenLogoutHandler implements LogoutHandler {

    private final AccessTokenCreator accessTokenCreator;

    public AccessTokenLogoutHandler(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = request.getHeader(AccessTokenAuthenticationFilter.ACCESS_TOKEN_HEADER);
        if (StringUtils.isNotBlank(accessToken)) {
            accessTokenCreator.invalidate(accessToken);
        }
    }
}
