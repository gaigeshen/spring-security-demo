package work.gaigeshen.spring.security.demo.security.web.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gaigeshen
 */
public abstract class AuthenticationFilter extends AbstractAuthenticationProcessingFilter implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    protected AuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        super(requestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("");
        }
        String password = request.getParameter("password");
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BadCredentialsException("");
        }
        AuthenticationToken token = AuthenticationToken.unauthenticated(username, password);
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(token);
    }
}
