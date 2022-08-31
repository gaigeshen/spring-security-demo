package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import work.gaigeshen.spring.security.demo.security.AuthenticationResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author gaigeshen
 */
public abstract class AuthenticationResultHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Override
    public final void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException {
        onFailure(req, resp, exception);
    }

    @Override
    public final void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        onSuccess(req, resp, (AuthenticationResult) authentication);
    }

    protected abstract void onFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException;

    protected abstract void onSuccess(HttpServletRequest req, HttpServletResponse resp, AuthenticationResult result) throws IOException;
}
