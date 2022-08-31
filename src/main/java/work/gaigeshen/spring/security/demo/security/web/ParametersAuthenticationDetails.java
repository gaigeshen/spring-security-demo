package work.gaigeshen.spring.security.demo.security.web;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class ParametersAuthenticationDetails {

    private final Map<String, String[]> parameters;

    public ParametersAuthenticationDetails(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String[]> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }
}
