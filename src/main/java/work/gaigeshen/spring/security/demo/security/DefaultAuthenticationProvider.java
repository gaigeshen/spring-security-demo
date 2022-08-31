package work.gaigeshen.spring.security.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 *
 * @author gaigeshen
 */
public class DefaultAuthenticationProvider extends AbstractAuthenticationProvider<DefaultAuthenticationToken> {

    private final UserDescriptorLoader descriptorLoader;

    private final PasswordEncoder passwordEncoder;

    public DefaultAuthenticationProvider(UserDescriptorLoader descriptorLoader, PasswordEncoder passwordEncoder) {
        this.descriptorLoader = descriptorLoader;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected UserDescriptor authenticate(DefaultAuthenticationToken token) throws AuthenticationException {
        UserDescriptor descriptor = descriptorLoader.load(token);
        if (Objects.isNull(descriptor)) {
            throw new UserDescriptorNotFoundException("user not found");
        }
        UserDescriptorStatusChecker.INSTANCE.check(descriptor);
        if (!passwordEncoder.matches(token.getPassword(), descriptor.getPassword())) {
            throw new PasswordInvalidException("user password invalid");
        }
        return descriptor;
    }
}
