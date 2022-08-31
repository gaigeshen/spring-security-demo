package work.gaigeshen.spring.security.demo.security.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gaigeshen
 */
public abstract class AccessTokenLogoutResultHandler implements LogoutResultHandler {

    private final AccessTokenCreator accessTokenCreator;

    public AccessTokenLogoutResultHandler(AccessTokenCreator accessTokenCreator) {
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
