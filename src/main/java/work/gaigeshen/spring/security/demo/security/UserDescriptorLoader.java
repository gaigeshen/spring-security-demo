package work.gaigeshen.spring.security.demo.security;

/**
 *
 * @author gaigeshen
 */
public interface UserDescriptorLoader {

    UserDescriptor load(AuthenticationToken token);
}
