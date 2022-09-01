package work.gaigeshen.spring.security.demo.security.web;

import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class WebAuthenticationDetails {

    private final String host;

    private final Map<String, String[]> parameters;

    public WebAuthenticationDetails(String host, Map<String, String[]> parameters) {
        this.host = host;
        this.parameters = parameters;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }
}
