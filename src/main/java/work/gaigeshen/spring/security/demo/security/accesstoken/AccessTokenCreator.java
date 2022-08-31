package work.gaigeshen.spring.security.demo.security.accesstoken;

import work.gaigeshen.spring.security.demo.security.UserDescriptor;

/**
 *
 * @author gaigeshen
 */
public interface AccessTokenCreator {

  void invalidate(String token);

  String createToken(UserDescriptor descriptor);

  UserDescriptor validateToken(String token);
}
