package work.gaigeshen.spring.security.demo.security.userdetails;

import work.gaigeshen.spring.security.demo.security.Authorization;

import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public interface UserDetails extends Authorization {

    Map<String, Object> getProperties();

    boolean isDisabled();

    boolean isLocked();
}
