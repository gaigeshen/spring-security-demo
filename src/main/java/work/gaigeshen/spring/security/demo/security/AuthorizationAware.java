package work.gaigeshen.spring.security.demo.security;

import work.gaigeshen.spring.security.demo.security.userdetails.UserDetails;

/**
 *
 * @author gaigeshen
 */
public interface AuthorizationAware {

    void setAuthorization(Authorization authorization);

    Authorization getAuthorization();

    default UserDetails getUserDetails() {
        Authorization authorization = getAuthorization();
        if (authorization instanceof UserDetails) {
            return (UserDetails) authorization;
        }
        return null;
    }
}
