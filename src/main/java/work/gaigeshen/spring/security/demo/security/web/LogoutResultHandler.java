package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author gaigeshen
 */
public interface LogoutResultHandler extends LogoutHandler, LogoutSuccessHandler {

    @Override
    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    @Override
    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException;
}
