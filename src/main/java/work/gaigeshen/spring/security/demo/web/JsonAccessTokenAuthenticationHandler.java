package work.gaigeshen.spring.security.demo.web;

import org.springframework.security.core.AuthenticationException;
import work.gaigeshen.spring.security.demo.commons.json.JsonCodec;
import work.gaigeshen.spring.security.demo.commons.web.ErrorResults;
import work.gaigeshen.spring.security.demo.commons.web.Results;
import work.gaigeshen.spring.security.demo.security.AuthenticatedToken;
import work.gaigeshen.spring.security.demo.security.Authorization;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.AbstractAccessTokenAuthenticationHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaigeshen
 */
public class JsonAccessTokenAuthenticationHandler extends AbstractAccessTokenAuthenticationHandler {

    public JsonAccessTokenAuthenticationHandler(AccessTokenCreator accessTokenCreator) {
        super(accessTokenCreator);
    }

    @Override
    protected void renderSuccessContent(HttpServletRequest req, HttpServletResponse resp, AuthenticatedToken token) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        Authorization authorization = token.getAuthorization();

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("userId", authorization.getUserId());
        resultData.put("username", authorization.getUsername());
        resultData.put("authorities", authorization.getAuthorities());

        resp.getWriter().write(JsonCodec.instance().encode(Results.create(resultData)));
    }

    @Override
    protected void handleFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().write(JsonCodec.instance().encode(ErrorResults.createResult(exception)));
    }
}
