package work.gaigeshen.spring.security.demo.security;

import java.util.Set;

public interface Authorization {

    String getUserId();

    String getUsername();

    Set<String> getAuthorities();
}
