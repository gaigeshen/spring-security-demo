package work.gaigeshen.spring.security.demo.security.web;

import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class WebParametersAdditionalAuthenticationDetails {

    private Map<String, String> parameters;

    public WebParametersAdditionalAuthenticationDetails(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
