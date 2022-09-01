package work.gaigeshen.spring.security.demo.security.web.logout;

import org.springframework.security.core.Authentication;
import work.gaigeshen.spring.security.demo.commons.json.JsonCodec;
import work.gaigeshen.spring.security.demo.commons.web.Results;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gaigeshen
 */
public class JsonAccessTokenLogoutHandler extends AccessTokenLogoutHandler {

    public JsonAccessTokenLogoutHandler(AccessTokenCreator accessTokenCreator) {
        super(accessTokenCreator);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().write(JsonCodec.instance().encode(Results.create()));
    }
}
