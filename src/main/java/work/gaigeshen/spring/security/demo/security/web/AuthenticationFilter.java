package work.gaigeshen.spring.security.demo.security.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import work.gaigeshen.spring.security.demo.security.AuthenticatingToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gaigeshen
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"), authenticationManager);
        setAuthenticationSuccessHandler(null);
        setAuthenticationFailureHandler(null);
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
        Object details = authenticationDetailsSource.buildDetails(request);

        AuthenticatingToken token = new AuthenticatingToken(username, password, details);

        return getAuthenticationManager().authenticate(token);
    }
}
