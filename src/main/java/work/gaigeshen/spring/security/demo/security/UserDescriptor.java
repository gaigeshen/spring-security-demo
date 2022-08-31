package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author gaigeshen
 */
public interface UserDescriptor extends UserDetails {

    @Override
    default Collection<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    default String getPassword() {
        return null;
    }

    @Override
    default String getUsername() {
        return null;
    }

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    default boolean isEnabled() {
        return true;
    }

    String getUserId();
}
