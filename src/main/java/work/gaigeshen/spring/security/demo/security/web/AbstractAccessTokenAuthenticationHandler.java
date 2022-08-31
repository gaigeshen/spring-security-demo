package work.gaigeshen.spring.security.demo.security.web;

import work.gaigeshen.spring.security.demo.security.AuthenticatedToken;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gaigeshen
 */
public abstract class AbstractAccessTokenAuthenticationHandler extends AuthenticationHandler {

    private final AccessTokenCreator accessTokenCreator;

    public AbstractAccessTokenAuthenticationHandler(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    protected void handleSuccess(HttpServletRequest req, HttpServletResponse resp, AuthenticatedToken token) throws IOException {
        String accessToken = accessTokenCreator.createToken(token.getAuthorization());
        resp.setHeader(AccessTokenAuthenticationFilter.ACCESS_TOKEN_HEADER, accessToken);
        renderSuccessContent(req, resp, token);
    }

    protected abstract void renderSuccessContent(HttpServletRequest req, HttpServletResponse resp, AuthenticatedToken token) throws IOException;
}
