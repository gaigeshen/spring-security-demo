package work.gaigeshen.spring.security.demo.security.web;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class WebParametersAdditionalAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebParametersAdditionalAuthenticationDetails> {

    @Override
    public WebParametersAdditionalAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new WebParametersAdditionalAuthenticationDetails(requestParameters(request));
    }

    private Map<String, String> requestParameters(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> {
            if (v.length > 0) {
                result.put(k, v[0]);
            }
        });
        return result;
    }
}
