package work.gaigeshen.spring.security.demo.security;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 * @author gaigeshen
 */
@EqualsAndHashCode(of = "userId")
@Builder
public class DefaultUserDescriptor implements UserDescriptor {

    private final String userId;

    private final String username;

    private final String password;

    private final Collection<GrantedAuthority> authorities;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
