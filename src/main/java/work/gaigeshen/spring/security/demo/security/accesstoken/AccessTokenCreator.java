package work.gaigeshen.spring.security.demo.security.accesstoken;

import work.gaigeshen.spring.security.demo.security.Authorization;

/**
 *
 * @author gaigeshen
 */
public interface AccessTokenCreator {

  void invalidate(String token);

  String createToken(Authorization authorization);

  Authorization validateToken(String token);
}
