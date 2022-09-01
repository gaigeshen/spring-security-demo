package work.gaigeshen.spring.security.demo.security.web.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import work.gaigeshen.spring.security.demo.security.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractLogoutHandler implements LogoutHandler, LogoutSuccessHandler {

    @Override
    public final void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logout(request, response, (AuthenticationToken) authentication);
    }

    @Override
    public final void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        onSuccess(request, response, (AuthenticationToken) authentication);
    }

    protected abstract void logout(HttpServletRequest request, HttpServletResponse response, AuthenticationToken token);

    protected abstract void onSuccess(HttpServletRequest request, HttpServletResponse response, AuthenticationToken token) throws IOException;
}
