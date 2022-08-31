package work.gaigeshen.spring.security.demo.security.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;
import work.gaigeshen.spring.security.demo.security.DefaultAuthenticationToken;
import work.gaigeshen.spring.security.demo.security.PasswordInvalidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gaigeshen
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username parameter not found");
        }
        String password = request.getParameter("password");
        if (StringUtils.isBlank(password)) {
            throw new PasswordInvalidException("password parameter not found");
        }
        AuthenticationToken authenticationToken = new DefaultAuthenticationToken(username, password);
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
