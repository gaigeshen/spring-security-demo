package work.gaigeshen.spring.security.demo.security;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractAuthorizationAware implements AuthorizationAware {

    private Authorization authorization;

    @Override
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public Authorization getAuthorization() {
        return authorization;
    }
}
