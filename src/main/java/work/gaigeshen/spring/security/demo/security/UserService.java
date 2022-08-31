package work.gaigeshen.spring.security.demo.security;

/**
 * @author gaigeshen
 */
public interface UserService {

    DefaultAuthorization findAuthorization(String username);

    UserPassword findUserPassword(String username);
}
