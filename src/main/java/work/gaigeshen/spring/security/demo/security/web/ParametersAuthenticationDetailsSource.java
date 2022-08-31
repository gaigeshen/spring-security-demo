package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaigeshen
 */
public class ParametersAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, ParametersAuthenticationDetails> {

    @Override
    public ParametersAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new ParametersAuthenticationDetails(request.getParameterMap());
    }
}
