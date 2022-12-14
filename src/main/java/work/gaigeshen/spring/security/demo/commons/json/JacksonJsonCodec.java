package work.gaigeshen.spring.security.demo.commons.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gaigeshen
 */
public class JacksonJsonCodec implements JsonCodec {

    public static final JacksonJsonCodec INSTANCE = new JacksonJsonCodec();

    private final ObjectMapper objectMapper;

    public JacksonJsonCodec(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonJsonCodec() {
        this(new ObjectMapper());
    }

    @Override
    public String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T decodeObject(String json, Class<T> resultClass) {
        try {
            return objectMapper.readValue(json, resultClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public <E> Collection<E> decodeCollection(String json, Class<E> itemClass) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<E>>() { });
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> decodeObject(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() { });
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Object> decodCollection(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Object>>() { });
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
