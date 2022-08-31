package work.gaigeshen.spring.security.demo.security.web;

import work.gaigeshen.spring.security.demo.security.AuthenticationResult;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractAccessTokenAuthenticationResultHandler extends AuthenticationResultHandler {

    private final AccessTokenCreator accessTokenCreator;

    public AbstractAccessTokenAuthenticationResultHandler(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, AuthenticationResult result) throws IOException {
        String accessToken = accessTokenCreator.createToken(result.getUserDescriptor());
        resp.setHeader(AccessTokenAuthenticationFilter.ACCESS_TOKEN_HEADER, accessToken);
        renderSuccessContent(req, resp, result);
    }

    protected abstract void renderSuccessContent(HttpServletRequest req, HttpServletResponse resp, AuthenticationResult result) throws IOException;
}
