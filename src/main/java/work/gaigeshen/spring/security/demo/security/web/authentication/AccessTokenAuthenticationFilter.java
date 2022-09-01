package work.gaigeshen.spring.security.demo.security.web.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;
import work.gaigeshen.spring.security.demo.security.Authorization;
import work.gaigeshen.spring.security.demo.security.accesstoken.AccessTokenCreator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String ACCESS_TOKEN_HEADER = "X-Auth-Token";

    private final AccessTokenCreator accessTokenCreator;

    public AccessTokenAuthenticationFilter(AccessTokenCreator accessTokenCreator) {
        this.accessTokenCreator = accessTokenCreator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String accessToken = req.getHeader(ACCESS_TOKEN_HEADER);
        if (StringUtils.isBlank(accessToken)) {
            chain.doFilter(req, resp);
            return;
        }
        Authorization authorization = accessTokenCreator.validateToken(accessToken);
        if (Objects.nonNull(authorization)) {
            SecurityContextHolder.getContext().setAuthentication(AuthenticationToken.authenticated(authorization));
        }
        chain.doFilter(req, resp);
    }
}
