package work.gaigeshen.spring.security.demo.security.accesstoken;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import work.gaigeshen.spring.security.demo.security.Authorization;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gaigeshen
 */
public class DefaultAccessTokenCreator implements AccessTokenCreator {

    private final Map<Authorization, String> authorizationTokens = new ConcurrentHashMap<>();

    private final Cache<String, Authorization> authorizations;

    private DefaultAccessTokenCreator(CacheBuilder<Object, Object> cacheBuilder) {
        authorizations = cacheBuilder.removalListener((RemovalListener<String, Authorization>) ntf -> {
            Authorization authorization = ntf.getValue();
            authorizationTokens.remove(authorization, ntf.getKey());
        }).build();
    }

    public static DefaultAccessTokenCreator create(long expiresInSeconds, long maxTokenCount) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(expiresInSeconds)).maximumSize(maxTokenCount);
        return new DefaultAccessTokenCreator(cacheBuilder);
    }

    public static DefaultAccessTokenCreator create() {
        return create(1800, 10000);
    }

    @Override
    public void invalidate(String token) {
        authorizations.invalidate(token);
    }

    @Override
    public void invalidate(Authorization authorization) {
        String token = authorizationTokens.get(authorization);
        if (Objects.nonNull(token)) {
            invalidate(token);
        }
    }

    @Override
    public String createToken(Authorization authorization) {
        String newToken = createTokenInternal();
        String previousToken = authorizationTokens.put(authorization, newToken);
        if (Objects.nonNull(previousToken)) {
            invalidate(previousToken);
        }
        this.authorizations.put(newToken, authorization);
        return newToken;
    }

    @Override
    public Authorization validateToken(String token) {
        return authorizations.getIfPresent(token);
    }

    private String createTokenInternal() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
