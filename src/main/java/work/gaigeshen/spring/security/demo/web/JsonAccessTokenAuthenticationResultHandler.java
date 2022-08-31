package work.gaigeshen.spring.security.demo.web;

import org.springframework.security.core.AuthenticationException;
import work.gaigeshen.spring.security.demo.commons.json.JsonCodec;
import work.gaigeshen.spring.security.demo.commons.web.Results;
import work.gaigeshen.spring.security.demo.commons.web.ErrorResults;
import work.gaigeshen.spring.security.demo.security.AuthenticationResult;
import work.gaigeshen.spring.security.demo.security.UserDescriptor;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;
import work.gaigeshen.spring.security.demo.security.web.AbstractAccessTokenAuthenticationResultHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class JsonAccessTokenAuthenticationResultHandler extends AbstractAccessTokenAuthenticationResultHandler {

    public JsonAccessTokenAuthenticationResultHandler(AccessTokenCreator accessTokenCreator) {
        super(accessTokenCreator);
    }

    @Override
    protected void renderSuccessContent(HttpServletRequest req, HttpServletResponse resp, AuthenticationResult result) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        UserDescriptor descriptor = result.getUserDescriptor();

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("userId", descriptor.getUserId());
        resultData.put("username", descriptor.getUsername());
        resultData.put("authorities", descriptor.getAuthorities());

        resp.getWriter().write(JsonCodec.instance().encode(Results.create(resultData)));
    }

    @Override
    protected void onFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().write(JsonCodec.instance().encode(ErrorResults.createResult(exception)));
    }
}
