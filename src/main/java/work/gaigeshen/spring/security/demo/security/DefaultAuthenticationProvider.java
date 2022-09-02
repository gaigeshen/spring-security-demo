package work.gaigeshen.spring.security.demo.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author gaigeshen
 */
public class DefaultAuthenticationProvider extends AbstractAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    @Override
    protected Authorization authenticate(AuthenticationToken token) throws AuthenticationException {
        log.info("authenticate token: {}", token);

        HttpServletRequest details = (HttpServletRequest) token.getDetails();
        String userId = details.getParameter("userId");
        String username = details.getParameter("username");
        String authoritiesStr = details.getParameter("authorities");

        if (StringUtils.isAnyBlank(userId, username, authoritiesStr)) {
            log.warn("could not find userId, username or authorities parameters");
            return null;
        }
        Set<String> authorities = Arrays.stream(authoritiesStr.split(",")).collect(Collectors.toSet());
        return new DefaultAuthorization(userId, username, authorities);
    }
}
