package work.gaigeshen.spring.security.demo.security.accesstoken;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import work.gaigeshen.spring.security.demo.security.UserDescriptor;

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

    private final Map<UserDescriptor, String> userDescriptorTokens = new ConcurrentHashMap<>();

    private final Cache<String, UserDescriptor> userDescriptors;

    private DefaultAccessTokenCreator(CacheBuilder<Object, Object> cacheBuilder) {
        userDescriptors = cacheBuilder.removalListener((RemovalListener<String, UserDescriptor>) ntf -> {
            UserDescriptor userDescriptor = ntf.getValue();
            userDescriptorTokens.remove(userDescriptor, ntf.getKey());
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
        userDescriptors.invalidate(token);
    }

    @Override
    public String createToken(UserDescriptor descriptor) {
        String newToken = createTokenInternal();
        String previousToken = userDescriptorTokens.put(descriptor, newToken);
        if (Objects.nonNull(previousToken)) {
            invalidate(previousToken);
        }
        userDescriptors.put(newToken, descriptor);
        return newToken;
    }

    @Override
    public UserDescriptor validateToken(String token) {
        return userDescriptors.getIfPresent(token);
    }

    private String createTokenInternal() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
