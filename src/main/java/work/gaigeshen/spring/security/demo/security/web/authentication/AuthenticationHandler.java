package work.gaigeshen.spring.security.demo.security.web.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gaigeshen
 */
public abstract class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Override
    public final void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        handleSuccess(req, resp, (AuthenticationToken) authentication);
    }

    @Override
    public final void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException {
        handleFailure(req, resp, exception);
    }

    protected abstract void handleSuccess(HttpServletRequest req, HttpServletResponse resp, AuthenticationToken token) throws IOException;

    protected abstract void handleFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException;
}
