package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author gaigeshen
 */
public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new WebAuthenticationDetails(request.getRemoteHost(), request.getParameterMap());
    }
}
