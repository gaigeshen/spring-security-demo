package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class SecurityContextUtils {

    public static UserDescriptor getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDescriptor) {
            return (UserDescriptor) principal;
        }
        return null;
    }
}
