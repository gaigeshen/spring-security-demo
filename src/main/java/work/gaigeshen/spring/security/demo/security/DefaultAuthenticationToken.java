package work.gaigeshen.spring.security.demo.security;

/**
 * @author gaigeshen
 */
public class DefaultAuthenticationToken extends AuthenticationToken {

    private final String username;

    private final String password;

    public DefaultAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
