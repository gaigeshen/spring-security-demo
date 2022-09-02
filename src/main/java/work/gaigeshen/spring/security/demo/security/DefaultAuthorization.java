package work.gaigeshen.spring.security.demo.security;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * 默认的授权信息
 *
 * @author gaigeshen
 */
public class DefaultAuthorization implements Authorization {

    private final String userId;

    private final String username;

    private final Set<String> authorities;

    public DefaultAuthorization(String userId, String username, Set<String> authorities) {
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Set<String> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultAuthorization that = (DefaultAuthorization) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return userId + "|" + username;
    }
}
