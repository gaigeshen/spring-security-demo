package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 *
 * @author gaigeshen
 */
public class UserDescriptorStatusChecker implements UserDetailsChecker {

    public static final UserDescriptorStatusChecker INSTANCE = new UserDescriptorStatusChecker();

    @Override
    public void check(UserDetails userDetails) {
        if (!(userDetails instanceof UserDescriptor)) {
            throw new IllegalArgumentException(userDetails.getClass() + " not supported");
        }
        UserDescriptor descriptor = (UserDescriptor) userDetails;
        if (!descriptor.isAccountNonLocked()) {
            throw new LockedException(descriptor.getUsername());
        }
        if (!descriptor.isEnabled()) {
            throw new DisabledException(descriptor.getUsername());
        }
        if (!descriptor.isAccountNonExpired()) {
            throw new AccountExpiredException(descriptor.getUsername());
        }
        if (!descriptor.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(descriptor.getUsername());
        }
    }
}
