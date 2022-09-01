package work.gaigeshen.spring.security.demo.security.accesstoken;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import work.gaigeshen.spring.security.demo.security.Authorization;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gaigeshen
 */
public abstract class AbstractAccessTokenCreator implements AccessTokenCreator {

    private final Map<Authorization, String> authorizationTokens = new ConcurrentHashMap<>();

    private final Cache<String, Authorization> authorizations;

    private AbstractAccessTokenCreator(CacheBuilder<Object, Object> cacheBuilder) {
        authorizations = cacheBuilder.removalListener((RemovalListener<String, Authorization>) ntf -> {
            Authorization authorization = ntf.getValue();
            authorizationTokens.remove(authorization, ntf.getKey());
        }).build();
    }

    protected AbstractAccessTokenCreator(long expiresSeconds, long maxTokenCount) {
        this(CacheBuilder.newBuilder().expireAfterAccess(Duration.ofSeconds(expiresSeconds)).maximumSize(maxTokenCount));
    }

    @Override
    public final void invalidate(String token) {
        authorizations.invalidate(token);
    }

    @Override
    public final void invalidate(Authorization authorization) {
        String token = authorizationTokens.get(authorization);
        if (Objects.nonNull(token)) {
            invalidate(token);
        }
    }

    @Override
    public final String createToken(Authorization authorization) {
        String newToken = createTokenInternal(authorization);
        String previousToken = authorizationTokens.put(authorization, newToken);
        if (Objects.nonNull(previousToken)) {
            invalidate(previousToken);
        }
        this.authorizations.put(newToken, authorization);
        return newToken;
    }

    @Override
    public final Authorization validateToken(String token) {
        Authorization authorization = authorizations.getIfPresent(token);
        if (Objects.isNull(authorization)) {
            return null;
        }
        if (validateTokenInternal(token, authorization)) {
            return authorization;
        }
        invalidate(token);
        return null;
    }

    protected abstract String createTokenInternal(Authorization authorization);

    protected abstract boolean validateTokenInternal(String token, Authorization authorization);
}
